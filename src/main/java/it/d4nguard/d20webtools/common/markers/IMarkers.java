package it.d4nguard.d20webtools.common.markers;

import java.util.Map;

public interface IMarkers
{
	public Map<String, IMarkerHandler> get();

	public IMarkerHandler put(String markerName, IMarkerHandler handler);

	public IMarkers putAll(Map<String, IMarkerHandler> entries);

	public void handle();
}
