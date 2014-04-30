package it.d4nguard.d20webtools.controller;

import static org.junit.Assert.*;
import it.d4nguard.d20webtools.BaseStrutsTestCase;
import it.d4nguard.d20webtools.SingleUserNavigation;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class WebSiteNavigator extends BaseStrutsTestCase<Action>
{
	@Test
	public final void testRunSingleNavigation()
	{
		try
		{
			new SingleUserNavigation(this, true, 0, false).runSingleNavigation();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			fail(sw.toString());
		}
	}

	@Override
	public boolean needsCreate()
	{
		return true;
	}
}
