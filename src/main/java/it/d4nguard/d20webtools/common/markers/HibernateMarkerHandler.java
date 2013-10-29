package it.d4nguard.d20webtools.common.markers;

import it.d4nguard.d20webtools.persistence.HibernateSession;

import java.util.Properties;

public class HibernateMarkerHandler implements IMarkerHandler
{
	private final Properties props;

	public HibernateMarkerHandler(Properties props)
	{
		this.props = (Properties) props.clone();
	}

	@Override
	public boolean handle(String name)
	{
		String[] split = name.split("#");
		props.put(split[0], split[1]);
		new HibernateSession(props).buildIfNeeded(true).close();
		return split[1].contentEquals(String.valueOf(props.remove(split[0])));
	}
}
