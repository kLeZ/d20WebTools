package it.d4nguard.d20webtools.controller;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;

import it.d4nguard.d20webtools.BaseStrutsTestCase;
import it.d4nguard.d20webtools.model.User;

public class SingleUserNavigation extends BaseStrutsTestCase<Action>
{
	private final String email;
	private final String username;
	private final String password;

	public SingleUserNavigation()
	{
		this(new User("kLeZ", "julius8774@gmail.com", "klez-hack87"));
	}

	public SingleUserNavigation(User u)
	{
		email = u.getEmail();
		username = u.getName();
		password = u.getPassword();
	}

	/**
	 * This method acts as a single user which navigates the webapp.<br>
	 * It is useful to have a single method handling an entire user session,
	 * because in this way I can think of calling it myself many times inside a
	 * thread pool.<br>
	 * Doing that can bring me to a (quasi) full true multiuser load test suite.
	 * 
	 * @throws Exception
	 */
	@Test
	public void runSingleNavigation() throws Exception
	{
		// First time: Registering user
		signUp();
		// When registered, enter the web site
		signIn();
	}

	private void signIn() throws Exception
	{
		Map<String, String> formUser1 = new HashMap<String, String>();
		formUser1.put("user.email", getEmail());
		formUser1.put("user.password", getPassword());

		request.setParameters(formUser1);

		ActionProxy proxy = getActionProxy("/pages/sign-in");

		assertTrue(proxy.getAction() instanceof SignIn);

		SignIn signIn = (SignIn) proxy.getAction();

		assertEquals(Action.SUCCESS, proxy.execute());
		assertEquals(0, signIn.getFieldErrors().size());
	}

	private void signUp() throws Exception
	{
		Map<String, String> formUser1 = new HashMap<String, String>();
		formUser1.put("user.name", getUsername());
		formUser1.put("user.email", getEmail());
		formUser1.put("user.password", getPassword());
		formUser1.put("confirmPassword", getPassword());

		request.setParameters(formUser1);

		ActionProxy proxy = getActionProxy("/pages/sign-up");

		assertTrue(proxy.getAction() instanceof SignUp);

		SignUp signUp = (SignUp) proxy.getAction();

		assertEquals(Action.SUCCESS, proxy.execute());
		assertEquals(0, signUp.getFieldErrors().size());
	}

	public String getEmail()
	{
		return email;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}
}
