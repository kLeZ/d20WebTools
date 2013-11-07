package it.d4nguard.d20webtools.persistence;

import java.util.List;

public interface IReturnValues<T>
{
	void setDone(boolean execute);

	void setSingle(T t);

	void setMany(List<T> list);

	boolean isDone();

	T getSingle();

	List<T> getMany();
}
