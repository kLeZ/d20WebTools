package it.d4nguard.d20webtools.controller;

import static org.junit.Assert.*;
import it.d4nguard.d20webtools.BaseStrutsTestCase;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;

public class SignUpTest extends BaseStrutsTestCase<SignUp>
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

			assertTrue(proxy.getAction() instanceof SignUp);

			SignUp signUp = (SignUp) proxy.getAction();

			assertEquals(Action.SUCCESS, proxy.execute());
			assertEquals(0, signUp.getFieldErrors().size());

			// Test duplicated user
			proxy = getActionProxy("/pages/sign-up");

			signUp = (SignUp) proxy.getAction();

			assertEquals(Action.SUCCESS, proxy.execute());
			assertEquals(1, signUp.getActionErrors().size());
		}
		catch (Exception e)
		{
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
}
