package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Message;
import it.d4nguard.d20webtools.model.Room;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringEscapeUtils;

@SuppressWarnings("deprecation")
public class ChatBox extends Session
{
	private static final long serialVersionUID = -7942241394133299564L;

	private Room room;
	private InputStream inputStream;

	@Override
	public String execute() throws Exception
	{
		synchronized (_session)
		{
			setRoom(RoomManager.ROOMS.get(_session.get(RoomManager.ROOM_ID)));
			StringBuilder sb = new StringBuilder();
			if (getRoom() != null)
			{
				if (!getRoom().getMessages().isEmpty())
				{
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
					for (Message msg : getRoom().getMessages())
					{
						sb.append("<div>");
						sb.append('[').append(sdf.format(msg.getTime())).append(']');
						sb.append(" <strong>").append(msg.getUser().getEmail()).append("</strong>: ");
						sb.append(StringEscapeUtils.escapeHtml4(msg.getText()));
						sb.append("</div>");
					}
				}
			}
			inputStream = new StringBufferInputStream(sb.toString());
		}
		return SUCCESS;
	}

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}
}
