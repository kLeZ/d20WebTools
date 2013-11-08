package it.d4nguard.d20webtools.common;

import java.util.Arrays;
import java.util.EnumSet;

public class Password
{
	public static String generate(int length, EnumSet<CharType> mods)
	{
		StringBuilder ret = new StringBuilder();
		if (!mods.isEmpty())
		{
			MersenneTwister random = new MersenneTwister();
			for (int i = 0; i < length; i++)
			{
				CharType type = mods.toArray(new CharType[] {})[random.next(0, mods.size() - 1)];
				char[] values = type.getValues();
				ret.append(values[random.next(0, values.length - 1)]);
			}
		}
		return ret.toString();
	}

	public static String mask(String s)
	{
		char[] chars = new char[s.length()];
		Arrays.fill(chars, '*');
		return new String(chars);
	}
}
