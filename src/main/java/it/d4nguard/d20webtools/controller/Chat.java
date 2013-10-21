package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.engine.Evaluator;
import it.d4nguard.d20webtools.model.Message;
import it.d4nguard.d20webtools.model.Room;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.opensymphony.xwork2.ModelDriven;

public class Chat extends Session implements ModelDriven<Room>
{
	private static final long serialVersionUID = 7405836330868983108L;

	private Room room;
	private String message;
	private InputStream chatboxContent;

	public Date now()
	{
		return Calendar.getInstance().getTime();
	}

	@Override
	public String execute() throws Exception
	{
		String ret = SUCCESS;
		synchronized (_session)
		{
			setRoom(RoomManager.ROOMS.get(_session.get(RoomManager.ROOM_ID)));
			messages();
			if (message != null && !message.trim().isEmpty())
			{
				Evaluator eval = new Evaluator();
				Message msg = new Message(now(), getUser(), eval.eval(message));
				getRoom().getMessages().add(msg);
				RoomManager.ROOMS.put(getRoom().getId(), getRoom());
			}
		}
		return ret;
	}

	public String messages() throws Exception
	{
		try
		{
			StringBuilder sb = new StringBuilder();
			if (getRoom() != null)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				sb.append("<div>");
				for (Message msg : getRoom().getMessages())
				{
					sb.append('[').append(sdf.format(msg.getTime())).append(']');
					sb.append(" <strong>").append(msg.getUser().getEmail()).append("</strong>: ");
					sb.append(msg.getText());
				}
				sb.append("</div>");
			}
			chatboxContent = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public InputStream getChatboxContent()
	{
		return chatboxContent;
	}

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	@Override
	public Room getModel()
	{
		return room;
	}
}
