package it.d4nguard.d20webtools.controller;

import static org.junit.Assert.*;
import it.d4nguard.d20webtools.BaseStrutsTestCase;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;

public class SignInTest extends BaseStrutsTestCase<SignIn>
{
	@Test
	public final void execute()
	{
		Map<String, String> formUser1 = new HashMap<String, String>();
		formUser1.put("user.email", "root@d20webtools.org");
		formUser1.put("user.password", "klez-hack87");

		try
		{
			request.setParameters(formUser1);

			ActionProxy proxy = getActionProxy("/pages/sign-in");

			assertTrue(proxy.getAction() instanceof SignIn);

			SignIn signIn = (SignIn) proxy.getAction();

			assertEquals(Action.SUCCESS, proxy.execute());
			assertEquals(0, signIn.getActionErrors().size());
			assertTrue(signIn.isLogged());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Override
	public boolean needsCreate()
	{
		return false;
	}
}
