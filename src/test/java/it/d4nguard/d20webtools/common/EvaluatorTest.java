package it.d4nguard.d20webtools.common;

import org.junit.Test;

public class EvaluatorTest
{
	@Test
	public final void testEval()
	{
		String input1 = "I'm rolling this 1d20 dice";
		String input2 = "Listen check of 1d20+15";
		String input3 = "Ok as a rogue I can use sneak attack for 1d6 + 2d6+4 of weapon + 10d8+43 of my magic item - 4d12 of the barbarian damage + 12d10 of the divine supremacy on the enemy + 3d4-2 of my multiclass wizard's enchanted dard + 1d10*2 of already casted meteor shower + 10d6/2 of fortune throw";
		String input4 = "Listen check of 1d20+15 and another Listen check of 1d20+15 if the last gone bad";

		Evaluator e = new Evaluator();

		System.out.println(input1);
		System.out.println(e.eval(input1));

		System.out.println(input2);
		System.out.println(e.eval(input2));

		System.out.println(input3);
		System.out.println(e.eval(input3));

		System.out.println(input4);
		System.out.println(e.eval(input4));
	}
}
