package it.d4nguard.d20webtools.persistence;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

public abstract class WriteOperation<T> implements PersistenceOperation<T>
{
	private final T payload;

	public WriteOperation(T t)
	{
		payload = t;
	}

	public void writeAll(Session session)
	{
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) payload;
		int i = 0;
		for (final Object obj : list)
		{
			singleWrite(session, obj);
			if (i++ % Persistor.FLUSH_RATE == 0)
			{
				session.flush();
				session.clear();
			}
		}
	}

	abstract void singleWrite(Session session, Object obj);

	@Override
	public ImplementedOperation getImplementedOperation()
	{
		return ImplementedOperation.Write;
	}

	@Override
	public IParameters<T> getParameters()
	{
		return new IParameters<T>()
		{
			@Override
			public Class<T> getTableClass()
			{
				return null;
			}

			@Override
			public Long getId()
			{
				return null;
			}

			@Override
			public String getString()
			{
				return null;
			}

			@Override
			public Object getObject()
			{
				return payload;
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
		};
	}

	@Override
	public IReturnValues<T> getReturnValues()
	{
		return null;
	}

	@Override
	public boolean execute(Session session, String sql)
	{
		return false;
	}

	@Override
	public T read(Session session, IParameters<T> obj)
	{
		return null;
	}

	@Override
	public List<T> readMany(Session session, IParameters<T> obj)
	{
		return null;
	}
}
