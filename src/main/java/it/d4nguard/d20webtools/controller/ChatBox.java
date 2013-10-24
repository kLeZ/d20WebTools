package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Message;
import it.d4nguard.d20webtools.model.Room;
import it.d4nguard.d20webtools.persistence.Persistor;

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
			if (getRoom() != null)
			{
				Persistor<Message> db = new Persistor<Message>(getHibernateFactory());
				Persistor<Room> db_r = new Persistor<Room>(getHibernateFactory());
				setRoom(db_r.findById(Room.class, new Long((Integer) _session.get(ROOM_ID))));
				List<Message> messages = db.findByEqField(Message.class, "room", _session.get(ROOM_ID));
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
