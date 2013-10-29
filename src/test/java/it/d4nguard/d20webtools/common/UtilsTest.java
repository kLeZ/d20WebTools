package it.d4nguard.d20webtools.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.d4nguard.d20webtools.common.Constants;
import it.d4nguard.d20webtools.common.Utils;

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
		String[] strings = new String[]
		{ DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD };
		assertTrue(Utils.containsAll(env.keySet(), DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD));
		assertTrue(Utils.containsAll(env.keySet(), strings));
		assertTrue(Utils.containsAll(env.keySet(), DB_HOST));
		assertFalse(Utils.containsAll(env.keySet(), DB_URL));
	}
}
