package it.d4nguard.d20webtools.common;

import it.d4nguard.d20webtools.persistence.HibernateFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HibernateListener implements ServletContextListener, Constants
{
	public void contextInitialized(ServletContextEvent evt)
	{
		evt.getServletContext().setAttribute(ENTITY_MANAGER, new HibernateFactory());
	}

	public void contextDestroyed(ServletContextEvent evt)
	{
		HibernateFactory factory = (HibernateFactory) evt.getServletContext().getAttribute(ENTITY_MANAGER);
		factory.closeFactory();
	}
}
