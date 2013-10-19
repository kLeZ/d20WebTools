package it.d4nguard.d20webtools.engine;

public abstract class AbstractEvaluator
{
	AbstractEvaluator next = null;

	public void setNext(AbstractEvaluator next)
	{
		this.next = next;
	}

	public abstract String eval(String message);
}
