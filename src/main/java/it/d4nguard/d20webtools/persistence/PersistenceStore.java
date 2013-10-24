package it.d4nguard.d20webtools.persistence;

public interface PersistenceStore
{
	public HibernateFactory getHibernateFactory();

	public Persistor getPersistor();
}
