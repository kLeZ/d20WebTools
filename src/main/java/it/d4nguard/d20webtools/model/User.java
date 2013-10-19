package it.d4nguard.d20webtools.model;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = 1272658304490658796L;

	private Account account;
	private String name;

	public User(Account account)
	{
		setAccount(account);
	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
