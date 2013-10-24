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
