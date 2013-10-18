package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Credential;

import com.opensymphony.xwork2.ActionSupport;

public class Session extends ActionSupport
{
	private static final long serialVersionUID = 6686337047561791387L;

	private boolean logged;
	private Credential credential;

	public Credential getCredential()
	{
		return credential;
	}

	public void setCredential(Credential credential)
	{
		this.credential = credential;
	}

	public boolean isLogged()
	{
		return logged;
	}

	public void setLogged(boolean logged)
	{
		this.logged = logged;
		if (!isLogged()) setCredential(null);
	}
}
