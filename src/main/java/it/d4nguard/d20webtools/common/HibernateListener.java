package it.d4nguard.d20webtools.common;

import it.d4nguard.d20webtools.common.markers.HibernateMarkerHandler;
import it.d4nguard.d20webtools.common.markers.IMarkers;
import it.d4nguard.d20webtools.common.markers.Markers;
import it.d4nguard.d20webtools.persistence.HibernateSession;
import it.d4nguard.d20webtools.persistence.Persistor;

import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

@WebListener
public class HibernateListener implements ServletContextListener, Constants
{
	private static final Logger log = Logger.getLogger(HibernateListener.class);

	private final Properties props;

	public HibernateListener()
	{
		props = new Properties();
	}

	public Properties getProps()
	{
		return props;
	}

	@Override
	public void contextInitialized(ServletContextEvent evt)
	{
		try
		{
			Map<String, String> env = System.getenv();
			if (Utils.containsAll(env.keySet(), DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD))
			{
				props.put(HIBERNATE_CONNECTION_URL, String.format(DB_URL, env.get(DB_HOST), env.get(DB_PORT)));
				props.put(HIBERNATE_CONNECTION_USERNAME, env.get(DB_USERNAME));
				props.put(HIBERNATE_CONNECTION_PASSWORD, env.get(DB_PASSWORD));
			}

			if (env.containsKey(APP_DIR))
			{
				IMarkers markers = new Markers(env.get(APP_DIR));
				markers.putAll(HibernateMarkerHandler.get(props)).handle();
			}
		}
		catch (Exception e)
		{
			log.warn("Unhandled exception with markers not so much important.", e);
		}

		Persistor persistor = null;
		try
		{
			persistor = new Persistor(new HibernateSession(props));
		}
		catch (HibernateException e)
		{
			log.fatal("There are problems with the database, please check connection", e);
		}
		evt.getServletContext().setAttribute(ENTITY_MANAGER, persistor);
	}

	@Override
	public void contextDestroyed(ServletContextEvent evt)
	{
		Persistor p = (Persistor) evt.getServletContext().getAttribute(ENTITY_MANAGER);
		if (p != null) p.destroy();
	}
}
