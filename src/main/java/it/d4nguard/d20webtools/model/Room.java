package it.d4nguard.d20webtools.model;

import java.util.LinkedHashSet;

public class Room
{
	private int id;
	private String name;
	private Account master;
	private LinkedHashSet<Account> members = new LinkedHashSet<Account>();
	private LinkedHashSet<String> messages = new LinkedHashSet<String>();

	public Room()
	{
	}

	public Room(int id, String name, String master)
	{
		setId(id);
		setName(name);
		setMaster(new Account(master, ""));
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Account getMaster()
	{
		return master;
	}

	public void setMaster(Account master)
	{
		this.master = master;
	}

	public LinkedHashSet<Account> getMembers()
	{
		return members;
	}

	public void setMembers(LinkedHashSet<Account> members)
	{
		this.members = members;
	}

	public LinkedHashSet<String> getMessages()
	{
		return messages;
	}

	public void setMessages(LinkedHashSet<String> messages)
	{
		this.messages = messages;
	}
}
