package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.common.Constants;
import it.d4nguard.d20webtools.model.User;
import it.d4nguard.d20webtools.persistence.HibernateSession;
import it.d4nguard.d20webtools.persistence.PersistenceStore;
import it.d4nguard.d20webtools.persistence.Persistor;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Session extends ActionSupport implements Constants, PersistenceStore
{
	private static final long serialVersionUID = 6686337047561791387L;

	private boolean logged;
	private User user;
	protected Map<String, Object> _session = ActionContext.getContext().getSession();

	@Override
	public String execute() throws Exception
	{
		return super.execute();
	}

	public User getUser()
	{
		if (user == null) user = (User) _session.get(SESSION_USER);
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public boolean isLogged()
	{
		if (!logged) logged = _session.containsKey(SESSION_LOGGED) && isSessionLogged();
		return logged;
	}

	private boolean isSessionLogged()
	{
		return Boolean.parseBoolean(String.valueOf(_session.get(SESSION_LOGGED)));
	}

	public void setLogged(boolean logged)
	{
		this.logged = logged;
		if (logged)
		{
			_session.put(SESSION_LOGGED, true);
			_session.put(SESSION_CONTEXT, new Date());
			_session.put(SESSION_USER, user);
		}
		else
		{
			_session.remove(SESSION_LOGGED);
			_session.remove(SESSION_CONTEXT);
			_session.remove(SESSION_USER);
			setUser(null);
		}
	}

	@Override
	public HibernateSession getHibernateFactory()
	{
		return getPersistor().getFactory();
	}

	@Override
	public Persistor getPersistor()
	{
		return (Persistor) ServletActionContext.getServletContext().getAttribute(ENTITY_MANAGER);
	}
}
