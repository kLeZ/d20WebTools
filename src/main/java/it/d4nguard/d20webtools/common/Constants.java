package it.d4nguard.d20webtools.common;

public interface Constants
{
	public static final String APP_DIR = "OPENSHIFT_DATA_DIR";

	public static final String DB_HOST = "OPENSHIFT_MYSQL_DB_HOST";
	public static final String DB_PORT = "OPENSHIFT_MYSQL_DB_PORT";
	public static final String DB_USERNAME = "OPENSHIFT_MYSQL_DB_USERNAME";
	public static final String DB_PASSWORD = "OPENSHIFT_MYSQL_DB_PASSWORD";

	public static final String DB_URL = "jdbc:mysql://%s:%s/d20webtools";

	public static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
	public static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
	public static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";

	public static final String HBM2DDL_AUTO_VALIDATE = "hibernate.hbm2ddl.auto#validate";
	public static final String HBM2DDL_AUTO_UPDATE = "hibernate.hbm2ddl.auto#update";
	public static final String HBM2DDL_AUTO_CREATE_DROP = "hibernate.hbm2ddl.auto#create-drop";
	public static final String HBM2DDL_AUTO_CREATE = "hibernate.hbm2ddl.auto#create";

	public static final String EXCEPTION = "Exception";
	public static final String ENTITY_MANAGER = "entity.manager";
	public static final String SESSION_ROOM_NAME = "room";
	public static final String SESSION_ROOM_ID = "roomId";
	public static final String SESSION_USER = "user";
	public static final String SESSION_LOGGED = "logged";
	public static final String SESSION_CONTEXT = "context";
}
