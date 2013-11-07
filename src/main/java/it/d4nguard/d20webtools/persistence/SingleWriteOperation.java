package it.d4nguard.d20webtools.persistence;

import org.hibernate.Session;

public abstract class SingleWriteOperation<T> extends WriteOperation<T>
{
	public SingleWriteOperation(T t)
	{
		super(t);
	}

	@Override
	void singleWrite(Session session, Object obj)
	{
	}
}
