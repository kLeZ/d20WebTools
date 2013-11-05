package it.d4nguard.d20webtools.common.markers;

import it.d4nguard.d20webtools.common.Constants;
import it.d4nguard.d20webtools.persistence.HibernateSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HibernateMarkerHandler implements IMarkerHandler, Constants
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

	public static Map<String, IMarkerHandler> get(Properties props)
	{
		Map<String, IMarkerHandler> hibernateMarkers = new HashMap<String, IMarkerHandler>();
		hibernateMarkers.put(HBM2DDL_AUTO_CREATE, new HibernateMarkerHandler(props));
		hibernateMarkers.put(HBM2DDL_AUTO_CREATE_DROP, new HibernateMarkerHandler(props));
		hibernateMarkers.put(HBM2DDL_AUTO_UPDATE, new HibernateMarkerHandler(props));
		hibernateMarkers.put(HBM2DDL_AUTO_VALIDATE, new HibernateMarkerHandler(props));
		return hibernateMarkers;
	}
}
