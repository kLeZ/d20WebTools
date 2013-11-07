package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.BaseStrutsTestCase;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;

public class SignUpTest extends BaseStrutsTestCase
{
	@Test
	public final void testExecute()
	{
		Map<String, String> formUser1 = new HashMap<String, String>();
		formUser1.put("user.name", "kLeZ");
		formUser1.put("user.email", "root@d20webtools.org");
		formUser1.put("user.password", "klez-hack87");
		formUser1.put("confirmPassword", "klez-hack87");
		try
		{
			request.setParameters(formUser1);

			ActionProxy proxy = getActionProxy("/pages/sign-up");

			SignUp signUp = (SignUp) proxy.getAction();

			assertEquals(Action.SUCCESS, proxy.execute());
			assertTrue(signUp.getFieldErrors().size() == 0);

			// Test duplicated user
			proxy = getActionProxy("/pages/sign-up");

			signUp = (SignUp) proxy.getAction();

			assertEquals(Action.SUCCESS, proxy.execute());
			assertTrue(signUp.getFieldErrors().size() == 1);
		}
		catch (Exception e)
		{
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
