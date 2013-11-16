package it.d4nguard.d20webtools;

import static org.junit.Assert.*;
import it.d4nguard.d20webtools.common.Constants;
import it.d4nguard.d20webtools.common.HibernateListener;
import it.d4nguard.d20webtools.common.StringUtils;
import it.d4nguard.d20webtools.persistence.HibernateSession;
import it.d4nguard.d20webtools.persistence.Persistor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.struts2.StrutsJUnit4TestCase;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseStrutsTestCase<T extends Action> extends StrutsJUnit4TestCase<T> implements Constants
{
	private Map<String, Object> session = new HashMap<String, Object>();

	public abstract boolean needsCreate();

	@Override
	protected void setupBeforeInitDispatcher() throws Exception
	{
		super.setupBeforeInitDispatcher();

		if (needsCreate())
		{
			Properties props = new Properties();
			props.put("hibernate.hbm2ddl.auto", "create");
			new HibernateSession(props).buildIfNeeded(true).close();
		}

		HibernateListener hibernateListener = new HibernateListener();
		ServletContextEvent event = new ServletContextEvent(servletContext);
		hibernateListener.contextInitialized(event);
	}

	public Persistor getPersistor()
	{
		return (Persistor) servletContext.getAttribute(ENTITY_MANAGER);
	}

	public <A extends ActionSupport> A callAction(Class<A> clazz, String actionPath) throws Exception
	{
		return callAction(clazz, actionPath, new HashMap<String, String>());
	}

	public <A extends ActionSupport> A callAction(Class<A> clazz, String actionPath, Map<String, String> form) throws Exception
	{
		request.removeAllParameters();

		ActionProxy proxy = getActionProxy(actionPath);

		request.setParameters(form);

		assertTrue(clazz.isAssignableFrom(proxy.getAction().getClass()));

		proxy.getInvocation().getInvocationContext().setSession(getSession());

		@SuppressWarnings("unchecked")
		A action = (A) proxy.getAction();
		String ret = proxy.execute();
		if (action.getActionErrors().size() > 0)
		{
			fail(StringUtils.join(StringUtils.LS, action.getActionErrors()));
		}
		if (action.getFieldErrors().size() > 0)
		{
			fail(StringUtils.join(StringUtils.LS, action.getActionErrors()));
		}
		assertEquals(Action.SUCCESS, ret);
		return action;
	}

	public Map<String, Object> getSession()
	{
		Enumeration<String> attrs = request.getSession().getAttributeNames();
		while (attrs.hasMoreElements())
		{
			String attr = attrs.nextElement();
			session.put(attr, request.getSession().getAttribute(attr));
		}
		return session;
	}
}
