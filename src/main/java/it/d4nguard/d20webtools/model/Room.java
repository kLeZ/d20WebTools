package it.d4nguard.d20webtools.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Room implements Serializable
{
	private static final long serialVersionUID = -1448219697531699908L;

	private Long id;
	private String name;
	private User master;
	private Set<Member> members = new LinkedHashSet<Member>();
	private Set<Message> messages = new LinkedHashSet<Message>();

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

	public Set<Member> getMembers()
	{
		return members;
	}

	public void setMembers(Set<Member> members)
	{
		this.members = members;
	}

	public Set<Message> getMessages()
	{
		return messages;
	}

	public void setMessages(Set<Message> messages)
	{
		this.messages = messages;
	}
}
