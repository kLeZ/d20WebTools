package it.d4nguard.d20webtools.common;

public enum CharType
{
	Upper(1, uppers()), Lower(2, lowers()), Number(4, numbers()), Symbol(8, symbols());

	private final int mod;
	private final char[] values;

	private CharType(int mod, char[] values)
	{
		this.mod = mod;
		this.values = values;
	}

	public int getMod()
	{
		return mod;
	}

	public char[] getValues()
	{
		return values;
	}

	public static char[] symbols()
	{
		// [33-47][58-64][91-96][123-126]
		char[] ret = new char[28];
		int i, j;
		for (i = 0, j = 0; i < 128 && j < 28; i++)
			if (i > 32 && i < 48 || i > 57 && i < 65 || i > 90 && i < 97 || i > 122 && i < 127) ret[j++] = (char) i;
		return ret;
	}

	public static char[] uppers()
	{
		char[] ret = new char[26];
		for (int i = 0; i < ret.length; i++)
			ret[i] = (char) (i + 97);
		return ret;
	}

	public static char[] lowers()
	{
		char[] ret = new char[26];
		for (int i = 0; i < ret.length; i++)
			ret[i] = (char) (i + 65);
		return ret;
	}

	public static char[] numbers()
	{
		return new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	}
}
