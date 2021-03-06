package it.d4nguard.d20webtools.common.markers;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Markers implements IMarkerHandler, IMarkers
{
	private final class MarkerFileFilter implements FileFilter
	{
		@Override
		public boolean accept(File pathname)
		{
			return pathname.isFile() && handlers.keySet().contains(pathname.getName());
		}
	}

	private final String path;
	private final Map<String, IMarkerHandler> handlers = new LinkedHashMap<String, IMarkerHandler>();

	public Markers(String path)
	{
		this.path = path;
	}

	public Markers(String path, Map<String, IMarkerHandler> handlers)
	{
		this(path);
		putAll(handlers);
	}

	public String getPath()
	{
		return path;
	}

	@Override
	public IMarkerHandler put(String markerName, IMarkerHandler handler)
	{
		return handlers.put(markerName, handler);
	}

	@Override
	public IMarkers putAll(Map<String, IMarkerHandler> entries)
	{
		handlers.putAll(entries);
		return this;
	}

	@Override
	public Map<String, IMarkerHandler> get()
	{
		return Collections.unmodifiableMap(handlers);
	}

	@Override
	public void handle()
	{
		handle(null);
	}

	@Override
	public boolean handle(String name)
	{
		File workspace = new File(path);
		if (workspace.exists() && workspace.isDirectory())
		{
			File[] files = workspace.listFiles(new MarkerFileFilter());
			for (File file : files)
			{
				IMarkerHandler marker = handlers.get(file.getName());
				if (marker.handle(file.getName())) file.delete();
			}
		}
		return true;
	}
}
