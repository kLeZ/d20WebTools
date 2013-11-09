package it.d4nguard.d20webtools.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable
{
	private static final long serialVersionUID = 1349171258128457798L;

	private Long id;
	private Date time;
	private User user;
	private String text;
	private Room room;

	/**
	 * Needed for struts2 to instantiate then populate, which is the default
	 * behavior of the framework.
	 */
	public Message()
	{
	}

	public Message(Date time, User user, String text, Room room)
	{
		super();
		this.time = time;
		this.user = user;
		this.text = text;
		this.room = room;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(Date time)
	{
		this.time = time;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (room == null ? 0 : room.hashCode());
		result = prime * result + (text == null ? 0 : text.hashCode());
		result = prime * result + (user == null ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Message)) return false;
		Message other = (Message) obj;
		if (room == null)
		{
			if (other.room != null) return false;
		}
		else if (!room.equals(other.room)) return false;
		if (text == null)
		{
			if (other.text != null) return false;
		}
		else if (!text.equals(other.text)) return false;
		if (user == null)
		{
			if (other.user != null) return false;
		}
		else if (!user.equals(other.user)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Message [id=");
		builder.append(id);
		builder.append(", time=");
		builder.append(time);
		builder.append(", user=");
		builder.append(user);
		builder.append(", text=");
		builder.append(text);
		builder.append(", room=");
		builder.append(room);
		builder.append("]");
		return builder.toString();
	}
}
