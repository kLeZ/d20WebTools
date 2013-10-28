package it.d4nguard.d20webtools.persistence;

public interface PersistenceStore
{
	public HibernateSession getHibernateFactory();

	public Persistor getPersistor();
}
