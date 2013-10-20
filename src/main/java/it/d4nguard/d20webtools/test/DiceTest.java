package it.d4nguard.d20webtools.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.d4nguard.d20webtools.engine.Dice;

import org.junit.Test;

public class DiceTest
{

	@Test
	public final void testIsDice()
	{
		assertTrue(Dice.isDice("1d20"));
		assertTrue(Dice.isDice("4d6"));
		assertTrue(Dice.isDice("6d6+25"));
		assertFalse(Dice.isDice("ciao mamma"));
		assertFalse(Dice.isDice(" "));
	}

	@Test
	public final void testIsManyDice()
	{
		assertTrue(Dice.isManyDice("(4d6)+(5d4+28)"));
		assertTrue(Dice.isManyDice("(1d20)+(1d10)+(3d8)+(4d12-10)"));
		assertFalse(Dice.isManyDice("ciao mamma"));
		assertFalse(Dice.isManyDice(" "));
	}

	@Test
	public final void testRollShowResults()
	{
		System.out.println(Dice.isManyDice("(4d6)+(5d4+28)"));
		assertTrue(Dice.isManyDice("(4d6)+(5d4+28)"));
		System.out.println(Dice.isManyDice("(1d20)+(1d10)+(3d8)+(4d12-10)"));
		assertTrue(Dice.isManyDice("(1d20)+(1d10)+(3d8)+(4d12-10)"));
		System.out.println(Dice.isManyDice("ciao mamma"));
		System.out.println(Dice.parseMany("ciao mamma"));
		assertFalse(Dice.isManyDice("ciao mamma"));
		System.out.println(Dice.isManyDice(" "));
		assertFalse(Dice.isManyDice(" "));
		System.out.println(Dice.rollShowResults("(4d6)+(5d4+28)"));
		System.out.println(Dice.rollShowResults("(1d20)+(1d10)+(3d8)+(4d12-10)"));
		System.out.println(Dice.rollShowResults("ciao mamma"));
		System.out.println(Dice.rollShowResults(" "));
	}

}
