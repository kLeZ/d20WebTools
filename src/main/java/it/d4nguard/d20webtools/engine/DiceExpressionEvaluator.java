package it.d4nguard.d20webtools.engine;

public class DiceExpressionEvaluator extends AbstractEvaluator
{
	@Override
	public boolean canManage(String message)
	{
		return Dice.isManyDice(message);
	}

	@Override
	protected String manage(String message)
	{
		return Dice.rollShowResults(message);
	}
}