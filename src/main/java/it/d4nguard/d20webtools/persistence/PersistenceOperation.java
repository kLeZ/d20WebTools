package it.d4nguard.d20webtools.persistence;

import java.util.List;

import org.hibernate.Session;

public interface PersistenceOperation<T>
{
	ImplementedOperation getImplementedOperation();

	IParameters<T> getParameters();

	IReturnValues<T> getReturnValues();

	T read(Session session, IParameters<T> obj) throws Exception;

	List<T> readMany(Session session, IParameters<T> obj) throws Exception;

	void write(Session session, Object obj) throws Exception;

	boolean execute(Session session, String sql) throws Exception;
}
