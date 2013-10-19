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
	protected Map<String, Object> _session = ActionContext.getContext().getSession();

	public Credential getCredential()
	{
		if (credential == null) credential = (Credential) _session.get("credential");
		return credential;
	}

	public void setCredential(Credential credential)
	{
		this.credential = credential;
	}

	public boolean isLogged()
	{
		if (!logged) logged = _session.containsKey("logged") && _session.get("logged") == "true";
		return logged;
	}

	public void setLogged(boolean logged)
	{
		this.logged = logged;
		if (isLogged())
		{
			_session.put("logged", "true");
			_session.put("context", new Date());
			_session.put("credential", credential);
		}
		else
		{
			_session.remove("logged");
			_session.remove("context");
			_session.remove("credential");
			setCredential(null);
		}
	}
}
