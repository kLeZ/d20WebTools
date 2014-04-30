package it.d4nguard.d20webtools.persistence;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

public class ReadOperation<E> implements PersistenceOperation<E>
{
	private final Class<E> clazz;
	private final Long id;
	private final ReadMethod readMethod;
	private E payload;

	public ReadOperation(Class<E> clazz, Long id, ReadMethod readMethod)
	{
		this.clazz = clazz;
		this.id = id;
		this.readMethod = readMethod;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E read(Session session, IParameters<E> obj)
	{
		E ret = null;
		switch (readMethod)
		{
			case Get:
				ret = (E) session.get(clazz, obj.getId());
				break;
			case Load:
				ret = (E) session.load(clazz, obj.getId());
				break;
			default:
				break;

		}
		return ret;
	}

	@Override
	public ImplementedOperation getImplementedOperation()
	{
		return ImplementedOperation.Read;
	}

	@Override
	public IParameters<E> getParameters()
	{
		return new IParameters<E>()
		{
			@Override
			public Class<E> getTableClass()
			{
				return clazz;
			}

			@Override
			public Long getId()
			{
				return id;
			}

			@Override
			public String getString()
			{
				return null;
			}

			@Override
			public Object getObject()
			{
				return null;
			}

			@Override
			public HashMap<String, String> getAliases()
			{
				return null;
			}

			@Override
			public HibernateRestriction[] getRestrictions()
			{
				return null;
			}

			@Override
			public FetchStrategy getFetchStrategy()
			{
				return null;
			}
		};
	}

	@Override
	public IReturnValues<E> getReturnValues()
	{
		return new IReturnValues<E>()
		{
			@Override
			public void setDone(boolean execute)
			{
			}

			@Override
			public void setSingle(E t)
			{
				payload = t;
			}

			@Override
			public void setMany(List<E> list)
			{
			}

			@Override
			public boolean isDone()
			{
				return false;
			}

			@Override
			public E getSingle()
			{
				return payload;
			}

			@Override
			public List<E> getMany()
			{
				return null;
			}
		};
	}

	@Override
	public List<E> readMany(Session session, IParameters<E> obj)
	{
		return null;
	}

	@Override
	public void write(Session session, Object obj)
	{
	}

	@Override
	public boolean execute(Session session, String sql)
	{
		return false;
	}
}
