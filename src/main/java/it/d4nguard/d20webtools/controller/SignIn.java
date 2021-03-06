package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Member;
import it.d4nguard.d20webtools.model.User;
import it.d4nguard.d20webtools.persistence.Persistor;
import it.d4nguard.d20webtools.persistence.PersistorException;

import java.util.List;

public class SignIn extends Session
{
	private static final long serialVersionUID = 6686337047561791387L;

	@Override
	public String execute() throws Exception
	{
		String ret = super.execute();
		Persistor persistor = getPersistor().manualFlush();
		if (!getUser().getEmail().trim().isEmpty()) try
		{
			List<User> users = persistor.findByEqField(User.class, "email", getUser().getEmail());
			if (users.size() == 1) ret = isValid(users.get(0));
			else if (users.size() > 1)
			{
				User first = users.remove(0);
				for (User u : users)
					persistor.delete(u);
				ret = isValid(first);
			}
			else
			{
				addActionError("User not registered, please sign up.");
				setLogged(false);
				ret = ERROR;
			}
		}
		catch (PersistorException e)
		{
			addActionError(e.getMessage());
			setLogged(false);
			ret = EXCEPTION;
		}
		else
		{
			setLogged(false);
			ret = LOGIN;
		}
		persistor.automaticFlush().flush();
		return ret;
	}

	private String isValid(User user)
	{
		String ret = SUCCESS;
		if (user.getPassword().contentEquals(getUser().getPassword()))
		{
			setUser(user);
			setLogged(true);
		}
		else
		{
			addActionError("Invalid password");
			setLogged(false);
			ret = LOGIN;
		}
		return ret;
	}

	public String logout() throws Exception
	{
		Persistor persistor = getPersistor().manualFlush();
		List<Member> members = persistor.findByEqField(Member.class, "user.id", getUser().getId());
		for (Member m : members)
			persistor.delete(m);
		setLogged(false);
		persistor.automaticFlush().flush();
		return SUCCESS;
	}
}
