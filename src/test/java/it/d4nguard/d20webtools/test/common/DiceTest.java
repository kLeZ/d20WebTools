package it.d4nguard.d20webtools.test.common;

import it.d4nguard.d20webtools.common.Dice;
import it.d4nguard.d20webtools.common.OperatorType;
import it.d4nguard.d20webtools.test.CollectionsAssert;

import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.Test;

public class DiceTest
{
	@Test
	public void testContains()
	{
		Assert.assertTrue(Dice.contains("I'm rolling this 1d20 dice"));
		Assert.assertTrue(Dice.contains("Listen check of 1d20+15"));
	}

	@Test
	public void testIsDice()
	{
		Assert.assertFalse(Dice.isDice("I'm rolling this 1d20 dice"));
		Assert.assertFalse(Dice.isDice("Listen check of 1d20+15"));
		Assert.assertTrue(Dice.isDice("1d20"));
		Assert.assertTrue(Dice.isDice("1d20+15"));
	}

	@Test
	public void testIsManyDice()
	{
		Assert.assertTrue(Dice.isManyDice("(1d6)(2d6+4)(10d8+43)-(4d12)(12d10)(3d4-2)(1d10*2)(10d6/2)"));
		Assert.assertTrue(Dice.isManyDice("1d6 2d6+4 10d8+43-4d12 12d10 3d4-2 1d10*2 10d6/2"));
		Assert.assertTrue(Dice.isManyDice("(1d6)+(2d6+4)+(10d8+43)-(4d12)+(12d10)+(3d4-2)+(1d10*2)+(10d6/2)"));
		Assert.assertTrue(Dice.isManyDice("1d6+2d6+4+10d8+43-4d12+12d10+3d4-2+1d10*2+10d6/2"));
		Assert.assertTrue(Dice.isManyDice("I'm rolling this 1d20 dice"));
		Assert.assertTrue(Dice.isManyDice("Listen check of 1d20+15"));
		Assert.assertTrue(Dice.isManyDice("4d6+36+5d4*3"));
		Assert.assertFalse(Dice.isManyDice("Ehilà!"));
		Assert.assertFalse(Dice.isManyDice("This is not a dice expression, even it is not a good sentence at all"));
		Assert.assertFalse(Dice.isManyDice("I'm regarding that this 1d 20 cannot be a 1 d1ce expression"));
	}

	@Test
	public void testParseMany()
	{
		String input1 = "(1d6)+(2d6+4)+(10d8+43)-(4d12)+(12d10)+(3d4-2)+(1d10*2)+(10d6/2)";
		String input2 = "(1d6)(2d6+4)(10d8+43)-(4d12)(12d10)(3d4-2)(1d10*2)(10d6/2)";
		String input3 = "1d6 + 2d6+4 + 10d8+43 - 4d12 + 12d10 + 3d4-2 + 1d10*2 + 10d6/2";
		String input4 = "1d6 2d6+4 10d8+43 - 4d12 12d10 3d4-2 1d10*2 10d6/2";
		String input5 = "Ok as a rogue I can use sneak attack for 1d6 + 2d6+4 of weapon + 10d8+43 of my magic item - 4d12 of the barbarian damage + 12d10 of the divine supremacy on the enemy + 3d4-2 of my multiclass wizard's enchanted dard + 1d10*2 of already casted meteor shower + 10d6/2 of fortune throw";

		LinkedHashMap<Dice, OperatorType> map = new LinkedHashMap<Dice, OperatorType>();
		map.put(new Dice(1, 6), OperatorType.Addition);
		map.put(new Dice(2, 6, OperatorType.Addition, 4), OperatorType.Addition);
		map.put(new Dice(10, 8, OperatorType.Addition, 43), OperatorType.Subtraction);
		map.put(new Dice(4, 12), OperatorType.Addition);
		map.put(new Dice(12, 10), OperatorType.Addition);
		map.put(new Dice(3, 4, OperatorType.Subtraction, 2), OperatorType.Addition);
		map.put(new Dice(1, 10, OperatorType.Multiplication, 2), OperatorType.Addition);
		map.put(new Dice(10, 6, OperatorType.Division, 2), null);

		CollectionsAssert.assertMapsEquals(map, Dice.parseMany(input1));
		CollectionsAssert.assertMapsEquals(map, Dice.parseMany(input2));
		CollectionsAssert.assertMapsEquals(map, Dice.parseMany(input3));
		CollectionsAssert.assertMapsEquals(map, Dice.parseMany(input4));
		CollectionsAssert.assertMapsEquals(map, Dice.parseMany(input5));
	}

	@Test
	public void testParse()
	{
		String input1 = "1d6";
		String input2 = "2d6+4";
		String input3 = "10d8+43";
		String input4 = "4d12";
		String input5 = "12d10";
		String input6 = "3d4-2";
		String input7 = "1d10*2";
		String input8 = "10d6/2";

		Dice dt1 = new Dice(1, 6);
		Dice dt2 = new Dice(2, 6, OperatorType.Addition, 4);
		Dice dt3 = new Dice(10, 8, OperatorType.Addition, 43);
		Dice dt4 = new Dice(4, 12);
		Dice dt5 = new Dice(12, 10);
		Dice dt6 = new Dice(3, 4, OperatorType.Subtraction, 2);
		Dice dt7 = new Dice(1, 10, OperatorType.Multiplication, 2);
		Dice dt8 = new Dice(10, 6, OperatorType.Division, 2);

		Assert.assertEquals(dt1, Dice.parse(input1));
		Assert.assertEquals(dt2, Dice.parse(input2));
		Assert.assertEquals(dt3, Dice.parse(input3));
		Assert.assertEquals(dt4, Dice.parse(input4));
		Assert.assertEquals(dt5, Dice.parse(input5));
		Assert.assertEquals(dt6, Dice.parse(input6));
		Assert.assertEquals(dt7, Dice.parse(input7));
		Assert.assertEquals(dt8, Dice.parse(input8));
	}
}
