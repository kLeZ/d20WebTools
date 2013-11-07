package it.d4nguard.d20webtools;

import it.d4nguard.d20webtools.common.HibernateListener;

import javax.servlet.ServletContextEvent;

import org.apache.struts2.StrutsTestCase;

public abstract class BaseStrutsTestCase extends StrutsTestCase
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		HibernateListener hibernateListener = new HibernateListener();
		hibernateListener.getProps().put("hibernate.hbm2ddl.auto", "create-drop");
		ServletContextEvent event = new ServletContextEvent(servletContext);
		hibernateListener.contextInitialized(event);
	}

	@Override
	protected void tearDown() throws Exception
	{
		HibernateListener hibernateListener = new HibernateListener();
		ServletContextEvent event = new ServletContextEvent(servletContext);
		hibernateListener.contextDestroyed(event);
		super.tearDown();
	}
}
