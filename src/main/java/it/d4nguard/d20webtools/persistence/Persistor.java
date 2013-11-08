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

public class Persistor
{
	public static final int FLUSH_RATE = 20;
	private static Logger log = Logger.getLogger(Persistor.class);

	private HibernateSession factory;

	public Persistor(HibernateSession factory)
	{
		this.factory = factory;
	}

	public HibernateSession getFactory()
	{
		return factory;
	}

	public synchronized void save(final Object obj)
	{
		doOperation(new SingleWriteOperation<Object>(obj)
		{
			@Override
			public void write(Session session, Object obj)
			{
				session.save(obj);
			}
		});
	}

	public synchronized void update(final Object obj)
	{
		doOperation(new SingleWriteOperation<Object>(obj)
		{
			@Override
			public void write(Session session, Object obj)
			{
				session.update(obj);
			}
		});
	}

	public synchronized void saveOrUpdate(final Object obj)
	{
		doOperation(new SingleWriteOperation<Object>(obj)
		{
			@Override
			public void write(Session session, Object obj)
			{
				session.saveOrUpdate(obj);
			}
		});
	}

	public synchronized void delete(final Object obj)
	{
		doOperation(new SingleWriteOperation<Object>(obj)
		{
			@Override
			public void write(Session session, Object obj)
			{
				session.delete(obj);
			}
		});
	}

	public synchronized void saveAll(final Collection<?> list)
	{
		doOperation(new ManyWriteOperation<Object>(list)
		{
			@Override
			void singleWrite(Session session, Object obj)
			{
				session.save(obj);
			}
		});
	}

	public synchronized void updateAll(final Collection<?> list)
	{
		doOperation(new ManyWriteOperation<Object>(list)
		{
			@Override
			void singleWrite(Session session, Object obj)
			{
				session.update(obj);
			}
		});
	}

	public synchronized void saveOrUpdateAll(final Collection<?> list)
	{
		doOperation(new ManyWriteOperation<Object>(list)
		{
			@Override
			void singleWrite(Session session, Object obj)
			{
				session.saveOrUpdate(obj);
			}
		});
	}

	public synchronized <E> E findById(final Class<E> clazz, final Long id)
	{
		return findById(clazz, id, ReadMethod.Get);
	}

	public synchronized <E> E findById(final Class<E> clazz, final Long id, ReadMethod readMethod)
	{
		return doOperation(new ReadOperation<E>(clazz, id, readMethod)).getSingle();
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
		return doOperation(new ReadManyOperation<E>(clazz)
		{
			@Override
			public List<E> readMany(Session session, IParameters<E> obj) throws Exception
			{
				final Criteria c = session.createCriteria(getParameters().getTableClass());
				for (final Entry<String, String> entry : aliases.entrySet())
				{
					c.createAlias(entry.getKey(), entry.getValue());
				}

				for (final HibernateRestriction restr : restrictions)
				{
					c.add(restr.toCriterion());
				}
				getReturnValues().setMany(c.list());
				return getReturnValues().getMany();
			}
		}).getMany();
	}

	@SuppressWarnings("unchecked")
	public synchronized <E> List<E> findAll(final Class<E> clazz)
	{
		return doOperation(new ReadManyOperation<E>(clazz)
		{
			@Override
			public List<E> readMany(Session session, IParameters<E> obj) throws Exception
			{
				Query query = session.createQuery("from " + getParameters().getTableClass().getName());
				getReturnValues().setMany(query.list());
				return getReturnValues().getMany();
			}
		}).getMany();
	}

	public synchronized boolean execute(final String sql)
	{
		return doOperation(new ExecuteOperation()
		{
			@Override
			public boolean execute(Session session, String sql) throws Exception
			{
				StatefulWork work = new StatefulWork(sql);
				session.doWork(work);
				return work.isWorkDone();
			}
		}).isDone();
	}

	public synchronized void destroy()
	{
		factory.closeFactory();
		factory = null;
	}

	public synchronized <T> IReturnValues<T> doOperation(PersistenceOperation<T> op)
	{
		IReturnValues<T> ret = op.getReturnValues();
		Session session = null;
		try
		{
			session = factory.openSession();
			session.beginTransaction();
			switch (op.getImplementedOperation())
			{
				case Read:
					ret.setSingle(op.read(session, op.getParameters()));
					break;
				case ReadMany:
					ret.setMany(op.readMany(session, op.getParameters()));
					break;
				case Write:
					op.write(session, op.getParameters().getObject());
					break;
				case Execute:
					ret.setDone(op.execute(session, op.getParameters().getString()));
					break;
			}
			session.getTransaction().commit();
		}
		catch (final Throwable e)
		{
			handleException(session, e);
		}
		finally
		{
			flushOperation(session, op);
		}
		return ret;
	}

	private <T> void flushOperation(Session session, PersistenceOperation<T> op)
	{
		if (session != null)
		{
			try
			{
				log.trace("Trying to close session object");
				session.close();
			}
			catch (final HibernateException ignored)
			{
				log.error("Couldn't close Session", ignored);
			}
			finally
			{
				session = null;
			}
		}
		op = null;
	}

	private void handleException(Session session, final Throwable e) throws PersistorException
	{
		try
		{
			if (session.getTransaction() != null)
			{
				log.trace("Transaction exists, trying to rollback");
				session.getTransaction().rollback();
			}
		}
		catch (Throwable t)
		{
			log.error("Couldn't rollback Transaction", t);
		}
		log.error(e, e);
		throw new PersistorException(e);
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
}
