package it.d4nguard.d20webtools.persistence;

public interface ChainedPersistorAction
{
	public Persistor execute(Persistor persistor);
}
