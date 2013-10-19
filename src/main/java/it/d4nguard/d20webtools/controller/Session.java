package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Account;

import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Session extends ActionSupport
{
	private static final long serialVersionUID = 6686337047561791387L;

	private boolean logged;
	private Account account;
	protected Map<String, Object> _session = ActionContext.getContext().getSession();

	public Account getAccount()
	{
		if (account == null) account = (Account) _session.get("account");
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
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
			_session.put("account", account);
		}
		else
		{
			_session.remove("logged");
			_session.remove("context");
			_session.remove("account");
			setAccount(null);
		}
	}
}
