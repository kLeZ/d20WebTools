package it.d4nguard.d20webtools.common;

import it.d4nguard.d20webtools.common.markers.HibernateMarkerHandler;
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
	public void contextInitialized(ServletContextEvent evt)
	{
		final Properties props = new Properties();
		Map<String, String> env = System.getenv();
		if (Utils.containsAll(env.keySet(), DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD, APP_DIR))
		{
			props.put(HIBERNATE_CONNECTION_URL, String.format(DB_URL, env.get(DB_HOST), env.get(DB_PORT)));
			props.put(HIBERNATE_CONNECTION_USERNAME, env.get(DB_USERNAME));
			props.put(HIBERNATE_CONNECTION_PASSWORD, env.get(DB_PASSWORD));

			Markers markers = new Markers(env.get(APP_DIR));
			markers.put(HBM2DDL_AUTO_CREATE, new HibernateMarkerHandler(props));
			markers.put(HBM2DDL_AUTO_CREATE_DROP, new HibernateMarkerHandler(props));
			markers.put(HBM2DDL_AUTO_UPDATE, new HibernateMarkerHandler(props));
			markers.put(HBM2DDL_AUTO_VALIDATE, new HibernateMarkerHandler(props));
			markers.handle();
		}
		evt.getServletContext().setAttribute(ENTITY_MANAGER, new Persistor(new HibernateSession(props)));
	}

	public void contextDestroyed(ServletContextEvent evt)
	{
		((Persistor) evt.getServletContext().getAttribute(ENTITY_MANAGER)).destroy();
	}
}
