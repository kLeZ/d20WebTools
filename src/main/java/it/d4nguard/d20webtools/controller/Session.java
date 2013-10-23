package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.common.Constants;
import it.d4nguard.d20webtools.model.User;

import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Session extends ActionSupport implements Constants
{
	private static final long serialVersionUID = 6686337047561791387L;

	private boolean logged;
	private User user;
	protected Map<String, Object> _session = ActionContext.getContext().getSession();

	public User getUser()
	{
		if (user == null) user = (User) _session.get("user");
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public boolean isLogged()
	{
		if (!logged) logged = _session.containsKey("logged") && _session.get("logged") == "true";
		return logged;
	}

	public void setLogged(boolean logged)
	{
		this.logged = logged;
		if (logged)
		{
			_session.put("logged", "true");
			_session.put("context", new Date());
			_session.put("user", user);
		}
		else
		{
			_session.remove("logged");
			_session.remove("context");
			_session.remove("user");
			setUser(null);
		}
	}
}
