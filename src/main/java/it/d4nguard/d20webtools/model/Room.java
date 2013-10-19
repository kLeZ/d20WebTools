package it.d4nguard.d20webtools.model;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class Room implements Serializable
{
	private static final long serialVersionUID = -1448219697531699908L;

	private int id;
	private String name;
	private Account master;
	private LinkedHashSet<Account> members = new LinkedHashSet<Account>();
	private LinkedHashSet<Message> messages = new LinkedHashSet<Message>();

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

	public LinkedHashSet<Message> getMessages()
	{
		return messages;
	}

	public void setMessages(LinkedHashSet<Message> messages)
	{
		this.messages = messages;
	}
}
