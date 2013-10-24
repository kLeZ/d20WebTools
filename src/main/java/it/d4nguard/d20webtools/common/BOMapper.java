package it.d4nguard.d20webtools.common;

public interface BOMapper<BO, BEAN>
{
	BO mapBean(BEAN bean);

	BEAN mapBO(BO bo);
}
