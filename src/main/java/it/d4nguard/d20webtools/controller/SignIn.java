package it.d4nguard.d20webtools.controller;

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
		String ret = SUCCESS;
		if (!getUser().getEmail().trim().isEmpty())
		{
			try
			{
				Persistor<User> db = new Persistor<User>();
				List<User> users = db.findByEqField(User.class, "email", getUser().getEmail());
				if (users.size() == 1)
				{
					setUser(users.get(0));
					setLogged(true);
				}
				else throw new PersistorException();
			}
			catch (PersistorException e)
			{
				addActionError(e.getLocalizedMessage());
				addActionError("User not registered. Please register!");
				setLogged(false);
				ret = ERROR;
			}
		}
		else
		{
			setLogged(false);
			ret = LOGIN;
		}
		return ret;
	}

	public String logout() throws Exception
	{
		setLogged(false);
		return SUCCESS;
	}
}
