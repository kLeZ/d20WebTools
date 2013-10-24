package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Message;
import it.d4nguard.d20webtools.model.Room;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

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
			StringBuilder sb = new StringBuilder();
			if (_session.get(ROOM_ID) != null)
			{
				setRoom(getPersistor().findById(Room.class, (Long) _session.get(ROOM_ID)));
				List<Message> messages = getPersistor().findByEqField(Message.class, "room.id", _session.get(ROOM_ID));
				if (!messages.isEmpty())
				{
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
					for (Message msg : messages)
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
