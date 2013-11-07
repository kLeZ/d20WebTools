package it.d4nguard.d20webtools.persistence;

import org.hibernate.Session;

public abstract class ManyWriteOperation<T> extends WriteOperation<T>
{
	public ManyWriteOperation(T t)
	{
		super(t);
	}

	@Override
	public void write(Session session, Object obj)
	{
		writeAll(session);
	}
}
