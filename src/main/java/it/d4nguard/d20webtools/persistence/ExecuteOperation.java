package it.d4nguard.d20webtools.persistence;

import java.util.List;

import org.hibernate.Session;

public abstract class ExecuteOperation implements PersistenceOperation<Object>
{
	@Override
	public ImplementedOperation getImplementedOperation()
	{
		return ImplementedOperation.Execute;
	}

	@Override
	public IParameters<Object> getParameters()
	{
		return null;
	}

	@Override
	public IReturnValues<Object> getReturnValues()
	{
		return null;
	}

	@Override
	public Object read(Session session, IParameters<Object> obj) throws Exception
	{
		return null;
	}

	@Override
	public List<Object> readMany(Session session, IParameters<Object> obj) throws Exception
	{
		return null;
	}

	@Override
	public void write(Session session, Object obj) throws Exception
	{
	}
}
