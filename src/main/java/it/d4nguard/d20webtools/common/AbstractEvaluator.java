package it.d4nguard.d20webtools.common;

public abstract class AbstractEvaluator
{
	private AbstractEvaluator next;

	public AbstractEvaluator getNext()
	{
		return next;
	}

	public AbstractEvaluator setNext(AbstractEvaluator next)
	{
		this.next = next;
		return this;
	}

	public abstract boolean canManage(String message);

	protected abstract String manage(String message);

	public String eval(String message)
	{
		if (canManage(message)) return manage(message);
		else if (getNext() != null) return getNext().eval(message);
		else return message;
	}

}
