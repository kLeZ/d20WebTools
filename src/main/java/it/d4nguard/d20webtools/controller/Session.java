package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Credential;

import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
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
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (isLogged())
		{
			session.put("logged", "true");
			session.put("context", new Date());
			session.put("credential", credential);
		}
		else
		{
			session.remove("logged");
			session.remove("context");
			session.remove("credential");
			setCredential(null);
		}
	}
}
