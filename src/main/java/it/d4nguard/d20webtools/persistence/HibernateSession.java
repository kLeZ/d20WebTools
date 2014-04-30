package it.d4nguard.d20webtools.persistence;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.w3c.dom.Document;

public class HibernateSession
{
	private static Logger log = Logger.getLogger(HibernateSession.class);

	private Configuration configuration;
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	private void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	private final Document config;
	private final Properties toOverrideProperties;
	private final Properties extraProperties;

	public HibernateSession()
	{
		this(null, null, null, false);
	}

	public HibernateSession(final boolean force)
	{
		this(null, null, null, force);
	}

	public HibernateSession(final Properties toOverrideProperties)
	{
		this(null, toOverrideProperties, null, false);
	}

	public HibernateSession(final Properties toOverrideProperties, final boolean force)
	{
		this(null, toOverrideProperties, null, force);
	}

	public HibernateSession(final Document config, final Properties toOverrideProperties)
	{
		this(config, toOverrideProperties, null, false);
	}

	public HibernateSession(final Document config, final Properties toOverrideProperties, final boolean force)
	{
		this(config, toOverrideProperties, null, force);
	}

	public HibernateSession(final Properties toOverrideProperties, final Properties extraProperties)
	{
		this(null, toOverrideProperties, extraProperties, false);
	}

	public HibernateSession(final Properties toOverrideProperties, final Properties extraProperties, final boolean force)
	{
		this(null, toOverrideProperties, extraProperties, force);
	}

	public HibernateSession(final Document config, final Properties toOverrideProperties, final Properties extraProperties, final boolean force)
	{
		this.config = config;
		this.toOverrideProperties = toOverrideProperties;
		this.extraProperties = extraProperties;
		buildIfNeeded(force);
	}

	public Document getConfig()
	{
		return config;
	}

	public Properties getOverrideProperties()
	{
		return toOverrideProperties;
	}

	public Properties getExtraProperties()
	{
		return extraProperties;
	}

	public Configuration getConfiguration()
	{
		if (configuration == null) configuration = new Configuration();
		return configuration;
	}

	public Session openSession() throws HibernateException
	{
		log.trace("Trying to open a session, CALLING { buildIfNeeded(config, toOverrideProperties, extraProperties) }");
		configureSessionFactory(false);
		log.trace("Built, opening session");
		return getSessionFactory().openSession();
	}

	public void closeFactory()
	{
		if (getSessionFactory() != null) try
		{
			log.trace("Closing sessionFactory session");
			if (getSessionFactory() instanceof SessionFactoryImpl)
			{
				SessionFactoryImpl sf = (SessionFactoryImpl) getSessionFactory();
				ConnectionProvider conn = sf.getConnectionProvider();
				if (conn instanceof C3P0ConnectionProvider) ((C3P0ConnectionProvider) conn).close();
			}
			getSessionFactory().close();
		}
		catch (final HibernateException e)
		{
			log.error("Couldn't close SessionFactory", e);
		}
		setSessionFactory(null);
	}

	/**
	 * @return
	 * @throws HibernateException
	 */
	private SessionFactory configureSessionFactory(final boolean force) throws HibernateException
	{
		if (force || sessionFactory == null)
		{
			if (configuration == null) if (config == null)
			{
				log.trace("Configuring Hibernate with default cfg file: CALLING { configuration.configure() }");
				getConfiguration().configure();
			}
			else
			{
				log.trace("Configuring Hibernate with provided cfg file: CALLING { configuration.configure(config) }");
				getConfiguration().configure(config);
			}

			if (toOverrideProperties != null && !toOverrideProperties.isEmpty())
			{
				log.trace("Overriding properties: { " + toOverrideProperties.toString() + " }");
				// Given the properties structure inside the configuration object,
				// I will replace its properties with mine and then merge the old,
				// taking only those extra properties not contained in my override

				/*
				 * The getProperties() method gives the integral properties object
				 * So I can choose to back it up
				 */
				final Properties old = getConfiguration().getProperties();

				/*
				 * The setProperties(Properties) method overrides directly with a
				 * variable reset
				 * So I can choose to override totally with my properties
				 */
				getConfiguration().setProperties(toOverrideProperties);

				/*
				 * The mergeProperties(Properties) method merges two Properties
				 * objects
				 * Given its decision to not to replace existing properties I can
				 * choose
				 * to pass the full old Properties object knowing that it will not
				 * replace my
				 * just set Properties.
				 */
				getConfiguration().mergeProperties(old);
			}

			if (extraProperties != null && !extraProperties.isEmpty())
			{
				log.trace("Adding extra properties: { " + extraProperties.toString() + " }");
				getConfiguration().addProperties(extraProperties);
			}

			log.trace("Building ServiceRegistry passing all the properties (configured and runtime added)");
			final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(getConfiguration().getProperties()).buildServiceRegistry();
			log.trace("Setting sessionFactory var: CALLING { sessionFactory = configuration.buildSessionFactory(serviceRegistry) }");
			setSessionFactory(getConfiguration().buildSessionFactory(serviceRegistry));
			log.trace("Well done, SessionFactory configured!");
		}
		return getSessionFactory();
	}

	/**
	 * Builds a SessionFactory, if it hasn't been already.
	 */
	public SessionFactory buildIfNeeded(final boolean force)
	{
		if (force)
		{
			log.trace("Forcing configure a new one");
			configuration = null;
			return configureSessionFactory(force);
		}
		if (getSessionFactory() != null)
		{
			log.trace("Cached sessionFactory, returning");
			return getSessionFactory();
		}
		try
		{
			log.trace("No sessionFactory, configuring a new one");
			return configureSessionFactory(force);
		}
		catch (final HibernateException e)
		{
			log.error(e, e);
			throw new PersistorException(e);
		}
	}
}
