package it.d4nguard.d20webtools.controller;

import static org.junit.Assert.*;
import it.d4nguard.d20webtools.BaseStrutsTestCase;
import it.d4nguard.d20webtools.SingleUserNavigation;

import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class WebSiteNavigator extends BaseStrutsTestCase<Action>
{
	@Test
	public final void testRunSingleNavigation()
	{
		SingleUserNavigation nav = new SingleUserNavigation(this);
		try
		{
			nav.runSingleNavigation();
		}
		catch (Exception e)
		{
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
