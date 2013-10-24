package it.d4nguard.d20webtools.persistence;

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

public class Persistor<E>
{
	private static Logger log = Logger.getLogger(Persistor.class);

	private Session session;
	private Transaction tx;
	private HibernateFactory factory;

	public Persistor(HibernateFactory factory)
	{
		this.factory = factory;
	}

	public void save(final E obj)
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
		finally
		{
			factory.close(session);
		}
	}

	public void update(final E obj)
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
		finally
		{
			factory.close(session);
		}
	}

	public void saveOrUpdate(final E obj)
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
		finally
		{
			factory.close(session);
		}
	}

	public void saveAll(final Collection<E> list)
	{
		try
		{
			startOperation();
			int i = 0;
			for (final E e : list)
			{
				session.save(e);
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
		finally
		{
			factory.close(session);
		}
	}

	public void updateAll(final Collection<E> list)
	{
		try
		{
			startOperation();
			int i = 0;
			for (final E e : list)
			{
				session.update(e);
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
		finally
		{
			factory.close(session);
		}
	}

	public void saveOrUpdateAll(final Collection<E> list)
	{
		try
		{
			startOperation();
			int i = 0;
			for (final E e : list)
			{
				session.saveOrUpdate(e);
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
		finally
		{
			factory.close(session);
		}
	}

	public void delete(final E obj)
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
		finally
		{
			factory.close(session);
		}
	}

	@SuppressWarnings("unchecked")
	public E findById(final Class<E> clazz, final Long id)
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
		finally
		{
			factory.close(session);
		}
		return obj;
	}

	public List<E> findByEqField(final Class<E> clazz, final String fieldName, final Object fieldValue)
	{
		return findByCriterion(clazz, new HibernateRestriction(BooleanOperatorType.eq, fieldName, fieldValue));
	}

	public List<E> findByCriterion(final Class<E> clazz, final HibernateRestriction... restrictions)
	{
		Pair<ArrayList<HibernateRestriction>, HashMap<String, String>> res = guessAliases(restrictions);
		return findByCriterion(clazz, res.getValue(), res.getKey().toArray(new HibernateRestriction[] {}));
	}

	@SuppressWarnings("unchecked")
	public List<E> findByCriterion(final Class<E> clazz, final HashMap<String, String> aliases, final HibernateRestriction... restrictions)
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
		finally
		{
			factory.close(session);
		}
		return objs;
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll(final Class<E> clazz)
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
		finally
		{
			factory.close(session);
		}
		return objects;
	}

	public boolean execute(final String sql)
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

	protected void handleException(final Throwable e) throws PersistorException
	{
		log.error(e, e);
		factory.rollback(tx);
		factory.close(session);
		session = null;
		throw new PersistorException(e);
	}

	protected void startOperation() throws HibernateException
	{
		session = factory.openSession();
		tx = session.beginTransaction();
	}

	public Pair<ArrayList<HibernateRestriction>, HashMap<String, String>> guessAliases(HibernateRestriction... restrictions)
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

	private Entry<String, Entry<String, String>> guessAlias(String param, final HashMap<String, String> aliases)
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
}
