package it.d4nguard.d20webtools;

import static org.junit.Assert.*;
import it.d4nguard.d20webtools.common.HibernateListener;
import it.d4nguard.d20webtools.persistence.HibernateSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.struts2.StrutsJUnit4TestCase;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseStrutsTestCase<T extends Action> extends StrutsJUnit4TestCase<T>
{
	@Override
	protected void setupBeforeInitDispatcher() throws Exception
	{
		super.setupBeforeInitDispatcher();

		Properties props = new Properties();
		props.put("hibernate.hbm2ddl.auto", "create");
		new HibernateSession(props).buildIfNeeded(true).close();

		HibernateListener hibernateListener = new HibernateListener();
		ServletContextEvent event = new ServletContextEvent(servletContext);
		hibernateListener.contextInitialized(event);
	}

	public <A extends ActionSupport> A callAction(Class<A> clazz, String actionPath) throws Exception
	{
		return callAction(clazz, actionPath, new HashMap<String, String>());
	}

	public <A extends ActionSupport> A callAction(Class<A> clazz, String actionPath, Map<String, String> form) throws Exception
	{
		System.out.println(request.getParameterMap());
		request.removeAllParameters();

		request.setParameters(form);

		ActionProxy proxy = getActionProxy(actionPath);

		assertTrue(clazz.isAssignableFrom(proxy.getAction().getClass()));

		proxy.getInvocation().getInvocationContext().setSession(getSession());

		@SuppressWarnings("unchecked")
		A action = (A) proxy.getAction();

		assertEquals(Action.SUCCESS, proxy.execute());
		assertEquals(0, action.getFieldErrors().size());

		return action;
	}

	public Map<String, Object> getSession()
	{
		Map<String, Object> session = new HashMap<String, Object>();
		Enumeration<String> attrs = request.getSession().getAttributeNames();
		while (attrs.hasMoreElements())
		{
			String attr = attrs.nextElement();
			session.put(attr, request.getSession().getAttribute(attr));
		}
		return session;
	}
}
