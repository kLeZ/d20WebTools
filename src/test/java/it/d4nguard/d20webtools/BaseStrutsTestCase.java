package it.d4nguard.d20webtools;

import it.d4nguard.d20webtools.common.HibernateListener;
import it.d4nguard.d20webtools.persistence.HibernateSession;

import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.apache.struts2.StrutsJUnit4TestCase;

import com.opensymphony.xwork2.Action;

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
}
