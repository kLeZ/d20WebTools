package it.d4nguard.d20webtools.persistence;

public interface BOMapper<BO, BEAN>
{
	BO mapBean(BEAN bean);

	BEAN mapBO(BO bo);
}
