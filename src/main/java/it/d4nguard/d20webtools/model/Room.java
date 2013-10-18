package it.d4nguard.d20webtools.model;

import java.util.Set;

public class Room
{
	private String name;
	private Credential master;
	private Set<Credential> members;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Credential getMaster()
	{
		return master;
	}

	public void setMaster(Credential master)
	{
		this.master = master;
	}

	public Set<Credential> getMembers()
	{
		return members;
	}

	public void setMembers(Set<Credential> members)
	{
		this.members = members;
	}
}
