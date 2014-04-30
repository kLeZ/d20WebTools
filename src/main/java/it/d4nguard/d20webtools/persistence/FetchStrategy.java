package it.d4nguard.d20webtools.persistence;

import org.hibernate.FetchMode;

public class FetchStrategy
{
	public static final FetchStrategy EMPTY = new FetchStrategy(null, null, 0, false);

	private final String associationPath;
	private final FetchMode mode;
	private final int size;
	private final boolean toBeUsed;

	public FetchStrategy(String associationPath, FetchMode mode, int size, boolean toBeUsed)
	{
		this.associationPath = associationPath;
		this.mode = mode;
		this.size = size;
		this.toBeUsed = toBeUsed;
	}

	public String getAssociationPath()
	{
		return associationPath;
	}

	public FetchMode getMode()
	{
		return mode;
	}

	public int getSize()
	{
		return size;
	}

	public boolean isToBeUsed()
	{
		return toBeUsed;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associationPath == null) ? 0 : associationPath.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + size;
		result = prime * result + (toBeUsed ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof FetchStrategy)) { return false; }
		FetchStrategy other = (FetchStrategy) obj;
		if (associationPath == null)
		{
			if (other.associationPath != null) { return false; }
		}
		else if (!associationPath.equals(other.associationPath)) { return false; }
		if (mode != other.mode) { return false; }
		if (size != other.size) { return false; }
		if (toBeUsed != other.toBeUsed) { return false; }
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FetchStrategy [associationPath=");
		builder.append(associationPath);
		builder.append(", mode=");
		builder.append(mode);
		builder.append(", size=");
		builder.append(size);
		builder.append(", toBeUsed=");
		builder.append(toBeUsed);
		builder.append("]");
		return builder.toString();
	}
}
