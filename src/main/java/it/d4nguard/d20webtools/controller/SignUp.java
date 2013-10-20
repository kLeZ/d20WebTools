package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.User;

import com.opensymphony.xwork2.ActionSupport;

public class SignUp extends ActionSupport
{
	private static final long serialVersionUID = -337136923511329022L;
	private String confirmPassword;
	private User user;

	@Override
	public String execute() throws Exception
	{
		return SUCCESS;
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
