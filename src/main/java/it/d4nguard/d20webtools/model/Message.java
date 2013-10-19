package it.d4nguard.d20webtools.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable
{
	private static final long serialVersionUID = 1349171258128457798L;

	private Date time;
	private User user;
	private String text;

	public Message(Date time, User user, String text)
	{
		this.time = time;
		this.user = user;
		this.text = text;
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
}
