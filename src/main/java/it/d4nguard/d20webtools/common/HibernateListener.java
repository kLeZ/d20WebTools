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

@WebListener
public class HibernateListener implements ServletContextListener, Constants
{
	private final Properties props;

	public HibernateListener()
	{
		props = new Properties();
	}

	public Properties getProps()
	{
		return props;
	}

	public void contextInitialized(ServletContextEvent evt)
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

		evt.getServletContext().setAttribute(ENTITY_MANAGER, new Persistor(new HibernateSession(props)));
	}

	public void contextDestroyed(ServletContextEvent evt)
	{
		Persistor p = (Persistor) evt.getServletContext().getAttribute(ENTITY_MANAGER);
		if (p != null) p.destroy();
	}
}
