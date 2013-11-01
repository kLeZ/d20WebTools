package it.d4nguard.d20webtools.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dice
{
	public static final Pattern pattern = Pattern.compile("(\\d)+[Dd](\\d){1,3}([+\\-*/](\\d)+)?");
	public static final String DICE_TOKEN = "D";

	private static final MersenneTwister random = new MersenneTwister(299792458);

	private final int nThrows;
	private final int nFaces;
	private final OperatorType modifierOperator;
	private final int modifier;
	private final LinkedList<Integer> diceResults;

	public Dice(int nThrows, int nFaces)
	{
		this(nThrows, nFaces, null, Integer.MIN_VALUE);
	}

	public Dice(int nThrows, int nFaces, OperatorType modifierOperator, int modifier)
	{
		this.nThrows = nThrows;
		this.nFaces = nFaces;
		this.modifierOperator = modifierOperator;
		this.modifier = modifier;
		diceResults = new LinkedList<Integer>();
	}

	/**
	 * @return the # throws to roll
	 */
	public int getnThrows()
	{
		return nThrows;
	}

	/**
	 * @return the # faces of this dice
	 */
	public int getnFaces()
	{
		return nFaces;
	}

	/**
	 * @return the modifier operator
	 */
	public OperatorType getModifierOperator()
	{
		return modifierOperator;
	}

	/**
	 * @return the modifier
	 */
	public int getModifier()
	{
		return modifier;
	}

	/**
	 * @return true if, and only if, this dice expression has a modifier,
	 *         otherwise false.
	 */
	private boolean hasModifier()
	{
		return (modifierOperator != null) && (modifier != Integer.MIN_VALUE);
	}

	public LinkedList<Integer> getDiceResults()
	{
		return diceResults;
	}

	public static boolean isDice(String s)
	{
		return pattern.matcher(s).matches();
	}

	public static boolean isManyDice(String s)
	{
		boolean ret = false;
		Matcher m = pattern.matcher(s);
		ret = m.find();
		if (ret) do
		{
			ret &= pattern.matcher(m.group()).matches();
		}
		while (m.find());
		return ret;
	}

	public static boolean contains(String s)
	{
		return pattern.matcher(s).find();
	}

	public static LinkedHashMap<Dice, OperatorType> parseMany(String s)
	{
		LinkedHashMap<Dice, OperatorType> ret = new LinkedHashMap<Dice, OperatorType>();
		Matcher m = pattern.matcher(s);
		int start = -1, end = -1;
		Dice d = null;
		while (m.find())
		{
			OperatorType ot = null;
			String grp = m.group();
			end = m.start();
			if (start >= 0 && (end > 0 && end < s.length()))
			{
				String op = s.substring(start, end);
				if (OperatorType.containsOperator(op)) ot = OperatorType.getOperator(op);
				else ot = OperatorType.Addition;
			}
			if (d != null && ret.get(d) == null) ret.put(d, ot);
			if (pattern.matcher(grp).matches()) d = Dice.parse(grp);
			if (d != null) ret.put(d, null);
			start = m.end();
		}
		return ret;
	}

	public static Dice parse(String s)
	{
		Dice ret = null;
		try
		{
			s = s.toUpperCase();
			String[] diceparts = s.split(DICE_TOKEN);
			if (diceparts.length == 2 && Utils.isInteger(diceparts[0]))
			{
				int nThrows = Integer.valueOf(diceparts[0]), nFaces = 0, modifier = 0;
				String nFacesBld = "";
				OperatorType opType = null;
				char[] faceParts = diceparts[1].toCharArray();
				for (char c : faceParts)
				{
					String current = String.valueOf(c);
					if (Utils.isInteger(current)) nFacesBld = nFacesBld.concat(current);
					else if ((opType = OperatorType.parseOperator(c)) != null) break;
					else break;
				}
				nFaces = Integer.valueOf(nFacesBld);

				if (opType != null)
				{
					String[] bonusParts = diceparts[1].split(opType.toEscapedString());
					if ((bonusParts.length == 2) && Utils.isInteger(bonusParts[1])) modifier = Integer.valueOf(bonusParts[1]);
					ret = new Dice(nThrows, nFaces, opType, modifier);
				}
				else ret = new Dice(nThrows, nFaces);
			}
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			ret = null;
		}
		return ret;
	}

	public static Integer rollSum(String diceExpression)
	{
		return rollSum(parseMany(diceExpression));
	}

	public static String rollShowResults(String diceExpression)
	{
		String ret = diceExpression;
		if (isManyDice(diceExpression))
		{
			LinkedHashMap<Dice, OperatorType> dice = Dice.parseMany(diceExpression);
			int res = 0;
			OperatorType op = OperatorType.Addition;
			for (Entry<Dice, OperatorType> e : dice.entrySet())
			{
				Dice d = e.getKey();
				String original = d.toString();
				Matcher matcher = Pattern.compile(getDiceRegex(original, false)).matcher(ret);
				res = op.doOperation(res, d.roll());
				op = e.getValue();
				if (matcher.find()) ret = StringUtils.replaceMatch(matcher, ret, d.toString());
				else
				{
					matcher = Pattern.compile(getDiceRegex(original, true)).matcher(ret);
					if (matcher.find()) ret = StringUtils.replaceMatch(matcher, ret, d.toString());
				}
			}
			ret = String.format("{%s} = %d", ret, res);
		}
		return ret;
	}

	public static Integer rollSum(LinkedHashMap<Dice, OperatorType> dice)
	{
		Integer ret = 0;
		Iterator<Map.Entry<Dice, OperatorType>> it = dice.entrySet().iterator();
		OperatorType op = null;
		while (it.hasNext())
		{
			Map.Entry<Dice, OperatorType> current = it.next();
			if (op != null) ret = op.doOperation(ret, current.getKey().roll());
			else /* First roll */ret = current.getKey().roll();
			op = current.getValue();
		}
		return ret;
	}

	public static ArrayList<Integer> roll(String diceExpression)
	{
		return roll(new LinkedHashSet<Dice>(parseMany(diceExpression).keySet()));
	}

	public static ArrayList<Integer> roll(LinkedHashSet<Dice> dice)
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		Iterator<Dice> it = dice.iterator();
		while (it.hasNext())
			ret.add(it.next().roll());
		return ret;
	}

	public static ArrayList<Integer> roll(Dice... dice)
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (Dice die : dice)
			ret.add(die.roll());
		return ret;
	}

	public Integer roll()
	{
		int ret = 0;
		for (int i = 0; i < getnThrows(); i++)
		{
			diceResults.add(random.next(1, getnFaces()));
			ret += diceResults.getLast();
		}
		if (hasModifier()) ret = getModifierOperator().doOperation(ret, getModifier());
		return ret;
	}

	private static String getDiceRegex(String s, boolean hitEnd)
	{
		return String.format("(?i)\\b(%s)%s", Pattern.quote(s), hitEnd ? "" : "[^=]");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	{
		boolean ret = false;
		if (o instanceof Dice)
		{
			ret = getnThrows() == ((Dice) o).getnThrows();
			ret &= getnFaces() == ((Dice) o).getnFaces();
			ret &= getModifierOperator() == ((Dice) o).getModifierOperator();
			ret &= getModifier() == ((Dice) o).getModifier();
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(nThrows).append(DICE_TOKEN).append(nFaces);
		if (modifierOperator != null) sb.append(modifierOperator).append(modifier);
		if (diceResults.size() > 0)
		{
			sb.append('=').append('{');

			int i = 0;
			for (Integer result : diceResults)
			{
				sb.append(result);
				if (diceResults.size() > ++i) sb.append("::");
			}
			sb.append('}');
		}
		return sb.toString();
	}
}
