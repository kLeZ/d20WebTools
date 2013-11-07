package it.d4nguard.d20webtools.persistence;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

public abstract class ReadManyOperation<E> implements PersistenceOperation<E>
{
	private final Class<E> clazz;
	private List<E> objs;

	public ReadManyOperation(Class<E> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public ImplementedOperation getImplementedOperation()
	{
		return ImplementedOperation.ReadMany;
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
			}

			@Override
			public void setMany(List<E> list)
			{
				objs = list;
			}

			@Override
			public boolean isDone()
			{
				return false;
			}

			@Override
			public E getSingle()
			{
				return null;
			}

			@Override
			public List<E> getMany()
			{
				return objs;
			}
		};
	}

	@Override
	public E read(Session session, IParameters<E> obj) throws Exception
	{
		return null;
	}

	@Override
	public void write(Session session, Object obj) throws Exception
	{
	}

	@Override
	public boolean execute(Session session, String sql) throws Exception
	{
		return false;
	}
}
