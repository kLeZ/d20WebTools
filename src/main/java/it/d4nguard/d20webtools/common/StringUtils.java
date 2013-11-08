package it.d4nguard.d20webtools.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;

public class StringUtils
{
	public static final char[] DIGIT_SYMBOLS = { '+', '-', '.', ',' };
	static
	{
		Arrays.sort(DIGIT_SYMBOLS);
	}

	public static boolean isNullOrWhitespace(final String s)
	{
		return s == null || s.isEmpty() || clean(s).isEmpty();
	}

	public static String clean(final String s)
	{
		return BlankRemover.itrim(BlankRemover.lrtrim(s)).toString();
	}

	public static String cleanDateRange(String s, final int take)
	{
		if (s != null)
		{
			s = s.contains("/") ? s.split("/").length > 0 ? s.split("/")[take] : s.substring(0, s.indexOf('/')) : s;
			s = s.contains("-") ? s.split("-").length > 0 ? s.split("-")[take] : s.substring(0, s.indexOf('-')) : s;
		}
		else s = "";
		return s;
	}

	public static String filterDigits(final String s)
	{
		return filterDigits(s, true);
	}

	public static String filterDigits(final String s, final boolean defaultToZero)
	{
		String ret = "";
		for (final char c : s.toCharArray())
			if (Character.isDigit(c) || Arrays.binarySearch(DIGIT_SYMBOLS, c) >= 0) ret = ret.concat(String.valueOf(c));
		if (defaultToZero && ret.isEmpty()) ret = "0";
		return ret;
	}

	public static <K, V> String join(final String separator, final Map<K, V> map)
	{
		final StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final Map.Entry<K, V> entry : map.entrySet())
		{
			sb.append("{ ");
			sb.append(String.valueOf(entry.getKey()));
			sb.append(", ");
			sb.append(String.valueOf(entry.getValue()));
			sb.append(" }");
			if (++i < map.size()) sb.append(separator);
		}
		return sb.toString();
	}

	public static <T> String join(final String separator, final Collection<T> coll)
	{
		final StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final T t : coll)
		{
			sb.append(String.valueOf(t));
			if (++i < coll.size()) sb.append(separator);
		}
		return sb.toString();
	}

	public static <T> String join(final String separator, final T... coll)
	{
		final StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final T t : coll)
		{
			sb.append(String.valueOf(t));
			if (++i < coll.length) sb.append(separator);
		}
		return sb.toString();
	}

	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of
	 * length 0.
	 * Note: Will return <code>true</code> for a CharSequence that purely
	 * consists of whitespace.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(final CharSequence str)
	{
		return str != null && str.length() > 0;
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of
	 * whitespace.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength(final String str)
	{
		return hasLength((CharSequence) str);
	}

	/**
	 * Check whether the given CharSequence has actual text.
	 * More specifically, returns <code>true</code> if the string not
	 * <code>null</code>,
	 * its length is greater than 0, and it contains at least one non-whitespace
	 * character.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>,
	 *         its length is greater than 0, and it does not contain whitespace
	 *         only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(final CharSequence str)
	{
		if (!hasLength(str)) return false;
		final int strLen = str.length();
		for (int i = 0; i < strLen; i++)
			if (!Character.isWhitespace(str.charAt(i))) return true;
		return false;
	}

	/**
	 * Check whether the given String has actual text.
	 * More specifically, returns <code>true</code> if the string not
	 * <code>null</code>,
	 * its length is greater than 0, and it contains at least one non-whitespace
	 * character.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not <code>null</code>, its
	 *         length is
	 *         greater than 0, and it does not contain whitespace only
	 * @see #hasText(CharSequence)
	 */
	public static boolean hasText(final String str)
	{
		return hasText((CharSequence) str);
	}

	public static String replaceMatch(Matcher matcher, String original, String replacement)
	{
		String start = original.substring(0, matcher.start());
		String end = original.substring(matcher.end());
		return String.format("%s%s%s", addSpace(start, true), replacement, addSpace(end, false));
	}

	public static String addSpace(String s, boolean append)
	{
		return s.length() > 0 ? (append ? s.endsWith(" ") ? s : s.concat(" ") : s.startsWith(" ") ? s : " ".concat(s)) : s;
	}
}
