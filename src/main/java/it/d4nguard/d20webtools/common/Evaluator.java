package it.d4nguard.d20webtools.common;

public class Evaluator extends AbstractEvaluator
{
	public Evaluator()
	{
		setNext(new DiceExpressionEvaluator());
	}

	@Override
	public boolean canManage(String message)
	{
		return false;
	}

	@Override
	protected String manage(String message)
	{
		return message;
	}
}
