package it.d4nguard.d20webtools.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class UtilsTest implements Constants
{
	private Map<String, String> env;

	@Before
	public final void before()
	{
		env = new HashMap<String, String>();
		env.put(DB_HOST, "localhost");
		env.put(DB_PORT, "3306");
		env.put(DB_USERNAME, "d20webtools");
		env.put(DB_PASSWORD, "d20WebTools001");
	}

	@Test
	public final void testContainsAll()
	{
		String[] strings = new String[] { DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD };
		assertTrue(Utils.containsAll(env.keySet(), DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD));
		assertTrue(Utils.containsAll(env.keySet(), strings));
		assertTrue(Utils.containsAll(env.keySet(), DB_HOST));
		assertFalse(Utils.containsAll(env.keySet(), DB_URL));
	}

	public void testSplitEncolosed()
	{
		char open = '(', close = ')';
		String input1 = "()()()()()()()()";
		String input2 = "(aaa)(bbb)(ccc)(ddd)(eee)(fff)(ggg)(hhh)";
		String input3 = "(aaa)()(ccc)()(eee)()(ggg)()";
		String input4 = "(aa)a(b)b(bc)c(c)c(c)d(d)d(e)e(e)ffgg";
		String input5 = "(a)aaa(b)b(b)b()f()r()c(c)e(e)g";

		String[] output1 = { "", "", "", "", "", "", "", "" };
		String[] output2 = { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh" };
		String[] output3 = { "aaa", "", "ccc", "", "eee", "", "ggg", "" };
		String[] output4 = { "aa", "a", "b", "b", "bc", "c", "c", "c", "c", "d", "d", "d", "e", "e", "e", "ffgg" };
		String[] output5 = { "a", "aaa", "b", "b", "b", "b", "", "f", "", "r", "", "c", "c", "e", "e", "g" };

		assertEquals(Arrays.asList(output1), Utils.splitEncolosed(input1, open, close));
		assertEquals(Arrays.asList(output2), Utils.splitEncolosed(input2, open, close));
		assertEquals(Arrays.asList(output3), Utils.splitEncolosed(input3, open, close));
		assertEquals(Arrays.asList(output4), Utils.splitEncolosed(input4, open, close));
		assertEquals(Arrays.asList(output5), Utils.splitEncolosed(input5, open, close));
	}
}
