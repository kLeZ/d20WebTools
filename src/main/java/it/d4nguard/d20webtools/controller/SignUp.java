package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.User;
import it.d4nguard.d20webtools.persistence.Persistor;
import it.d4nguard.d20webtools.persistence.PersistorException;

public class SignUp extends Session
{
	private static final long serialVersionUID = -337136923511329022L;
	private String confirmPassword;
	private User user;

	@Override
	public String execute() throws Exception
	{
		String ret = SUCCESS;
		Persistor<User> db = new Persistor<User>(getHibernateFactory());
		try
		{
			db.save(getUser());
			addActionMessage("User registered successfully!");
		}
		catch (PersistorException e)
		{
			addActionError(e.getLocalizedMessage());
			addActionError(String.format("User %s is already registered!", getUser().getEmail()));
			ret = EXCEPTION;
		}
		return ret;
	}

	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}
