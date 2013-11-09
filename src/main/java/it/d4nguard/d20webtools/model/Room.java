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

	/**
	 * Needed for struts2 to instantiate then populate, which is the default
	 * behavior of the framework.
	 */
	public Room()
	{
	}

	public Room(String name, String master)
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Room)) return false;
		Room other = (Room) obj;
		if (name == null)
		{
			if (other.name != null) return false;
		}
		else if (!name.equals(other.name)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Room [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", master=");
		builder.append(master);
		builder.append(", members=");
		builder.append(members);
		builder.append(", messages=");
		builder.append(messages);
		builder.append("]");
		return builder.toString();
	}
}
