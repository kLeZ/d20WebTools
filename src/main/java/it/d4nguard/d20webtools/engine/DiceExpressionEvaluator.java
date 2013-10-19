package it.d4nguard.d20webtools.engine;

import java.util.LinkedHashMap;
import java.util.Map;

public class DiceExpressionEvaluator extends AbstractEvaluator
{
	@Override
	public String eval(String message)
	{
		LinkedHashMap<Dice, OperatorType> dl = Dice.parseMany(message);
		if (dl != null && !dl.isEmpty())
		{
			for (Map.Entry<Dice, OperatorType> e : dl.entrySet())
			{
				Dice d = e.getKey();
				//TODO: Roll Dice!
				switch (e.getValue())
				{
					case Addition:
						break;
					case Division:
						break;
					case Multiplication:
						break;
					case Subtraction:
						break;
				}
			}
			return "";
		}
		else return next.eval(message);
	}
}
