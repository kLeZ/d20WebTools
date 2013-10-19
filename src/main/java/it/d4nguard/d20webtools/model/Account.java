package it.d4nguard.d20webtools.model;

import java.io.Serializable;

public class Account implements Serializable
{
	private static final long serialVersionUID = 4109094925530070042L;

	private String email;
	private String password;

	public Account()
	{
	}

	public Account(String email, String password)
	{
		super();
		this.email = email;
		this.password = password;
	}

	public String getWebEmail()
	{
		return antiPhishing(email);
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public static String antiPhishing(String email)
	{
		String temp = email.replace("@", " [@] ");
		return temp.substring(0, temp.lastIndexOf('.')).concat(" [.] ").concat(temp.substring(temp.lastIndexOf('.') + 1));
	}
}
