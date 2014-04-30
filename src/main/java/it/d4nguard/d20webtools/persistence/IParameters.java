package it.d4nguard.d20webtools.persistence;

import java.util.HashMap;

public interface IParameters<T>
{
	Class<T> getTableClass();

	Long getId();

	String getString();

	Object getObject();

	HashMap<String, String> getAliases();

	HibernateRestriction[] getRestrictions();

	FetchStrategy getFetchStrategy();
}
