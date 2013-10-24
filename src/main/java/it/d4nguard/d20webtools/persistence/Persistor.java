package it.d4nguard.d20webtools.persistence;

import it.d4nguard.d20webtools.common.BooleanOperatorType;
import it.d4nguard.d20webtools.common.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Persistor
{
	private static Logger log = Logger.getLogger(Persistor.class);

	private Session session;
	private Transaction tx;
	private HibernateFactory factory;

	public Persistor(HibernateFactory factory)
	{
		this.factory = factory;
	}

	public synchronized void close()
	{
		factory.close(session);
		session = null;
	}

	public synchronized void destroy()
	{
		close();
		factory.closeFactory();
	}

	public synchronized boolean contains(Object object)
	{
		return session != null && session.contains(object);
	}

	public synchronized void save(final Object obj)
	{
		try
		{
			startOperation();
			session.save(obj);
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
	}

	public synchronized void update(final Object obj)
	{
		try
		{
			startOperation();
			session.update(obj);
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
	}

	public synchronized void saveOrUpdate(final Object obj)
	{
		try
		{
			startOperation();
			session.saveOrUpdate(obj);
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
	}

	public synchronized void saveAll(final Collection<Object> list)
	{
		try
		{
			startOperation();
			int i = 0;
			for (final Object obj : list)
			{
				session.save(obj);
				if ((i++ % 20) == 0)
				{
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
	}

	public synchronized void updateAll(final Collection<Object> list)
	{
		try
		{
			startOperation();
			int i = 0;
			for (final Object obj : list)
			{
				session.update(obj);
				if ((i++ % 20) == 0)
				{
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
	}

	public synchronized void saveOrUpdateAll(final Collection<Object> list)
	{
		try
		{
			startOperation();
			int i = 0;
			for (final Object obj : list)
			{
				session.saveOrUpdate(obj);
				if ((i++ % 20) == 0)
				{
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
	}

	public synchronized void delete(final Object obj)
	{
		try
		{
			startOperation();
			session.delete(obj);
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized <E> E findById(final Class<E> clazz, final Long id)
	{
		E obj = null;
		try
		{
			startOperation();
			obj = (E) session.load(clazz, id);
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
		return obj;
	}

	public synchronized <E> List<E> findByEqField(final Class<E> clazz, final String fieldName, final Object fieldValue)
	{
		return findByCriterion(clazz, new HibernateRestriction(BooleanOperatorType.eq, fieldName, fieldValue));
	}

	public synchronized <E> List<E> findByCriterion(final Class<E> clazz, final HibernateRestriction... restrictions)
	{
		Pair<ArrayList<HibernateRestriction>, HashMap<String, String>> res = guessAliases(restrictions);
		return findByCriterion(clazz, res.getValue(), res.getKey().toArray(new HibernateRestriction[] {}));
	}

	@SuppressWarnings("unchecked")
	public synchronized <E> List<E> findByCriterion(final Class<E> clazz, final HashMap<String, String> aliases, final HibernateRestriction... restrictions)
	{
		List<E> objs = null;
		try
		{
			startOperation();
			final Criteria c = session.createCriteria(clazz);
			for (final Entry<String, String> entry : aliases.entrySet())
			{
				c.createAlias(entry.getKey(), entry.getValue());
			}

			for (final HibernateRestriction restr : restrictions)
			{
				c.add(restr.toCriterion());
			}
			objs = c.list();
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
		return objs;
	}

	@SuppressWarnings("unchecked")
	public synchronized <E> List<E> findAll(final Class<E> clazz)
	{
		List<E> objects = null;
		try
		{
			startOperation();
			final Query query = session.createQuery("from " + clazz.getName());
			objects = query.list();
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
		return objects;
	}

	public synchronized boolean execute(final String sql)
	{
		boolean ret = false;
		try
		{
			startOperation();
			final StatefulWork work = new StatefulWork(sql);
			session.doWork(work);
			ret = work.isWorkDone();
			tx.commit();
		}
		catch (final Throwable e)
		{
			handleException(e);
		}
		finally
		{
			factory.close(session);
		}
		return ret;
	}

	protected synchronized void handleException(final Throwable e) throws PersistorException
	{
		log.error(e, e);
		factory.rollback(tx);
		factory.close(session);
		session = null;
		throw new PersistorException(e);
	}

	protected synchronized void startOperation() throws HibernateException
	{
		if (session == null || (!session.isConnected() || session.isDirty() || !session.isOpen())) session = factory.openSession();
		synchronized (session)
		{
			tx = session.beginTransaction();
		}
	}

	private static Pair<ArrayList<HibernateRestriction>, HashMap<String, String>> guessAliases(HibernateRestriction... restrictions)
	{
		HashMap<String, String> aliases = new HashMap<String, String>();
		ArrayList<HibernateRestriction> res = new ArrayList<HibernateRestriction>();
		for (HibernateRestriction hr : restrictions)
		{
			Entry<String, Entry<String, String>> guess = guessAlias(hr.getField(), aliases);
			if (guess.getValue() != null)
			{
				aliases.put(guess.getValue().getKey(), guess.getValue().getValue());
				res.add(new HibernateRestriction(hr.getOperator(), guess.getKey(), hr.getValue()));
			}
			else res.add(hr);
		}
		return new Pair<ArrayList<HibernateRestriction>, HashMap<String, String>>(res, aliases);
	}

	private static Entry<String, Entry<String, String>> guessAlias(String param, final HashMap<String, String> aliases)
	{
		Pair<String, String> alias = null;
		final String field;
		if (param.indexOf('.') >= 0)
		{
			final String[] split = param.split("\\.");
			String aliasLetter = "";
			if (!aliases.containsKey(split[0]))
			{
				/*
				 * Lower case letters, ASCII standard is from 97(a) to 122(z)
				 * When the ASCII standard will change this will stop to work
				 * (even the rest of the world will stop to work)
				 */
				for (char c = 122; c > 96 && aliasLetter.isEmpty(); c--)
					if (!aliases.containsValue(String.valueOf(c))) aliasLetter = String.valueOf(c);
			}
			else aliasLetter = aliases.get(split[0]);
			alias = new Pair<String, String>(split[0], aliasLetter);
			field = String.format("%s.%s", aliasLetter, split[1]);
		}
		else field = param;
		return new Pair<String, Entry<String, String>>(field, alias);
	}

	public Session getSession()
	{
		return session;
	}

	public HibernateFactory getFactory()
	{
		return factory;
	}
}
