package it.d4nguard.d20webtools.model;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class Room implements Serializable
{
	private static final long serialVersionUID = -1448219697531699908L;

	private Long id;
	private String name;
	private User master;
	private LinkedHashSet<Member> members = new LinkedHashSet<Member>();
	private LinkedHashSet<Message> messages = new LinkedHashSet<Message>();

	public Room()
	{
	}

	public Room(Long id, String name, String master)
	{
		setId(id);
		setName(name);
		setMaster(new User(master, master, ""));
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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

	public User getMaster()
	{
		return master;
	}

	public void setMaster(User master)
	{
		this.master = master;
	}

	public LinkedHashSet<Member> getMembers()
	{
		return members;
	}

	public void setMembers(LinkedHashSet<Member> members)
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
