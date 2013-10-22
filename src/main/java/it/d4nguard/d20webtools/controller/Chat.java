package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.common.Evaluator;
import it.d4nguard.d20webtools.model.Message;
import it.d4nguard.d20webtools.model.Room;
import it.d4nguard.d20webtools.persistence.Persistor;

import java.util.Calendar;
import java.util.Date;

import com.opensymphony.xwork2.ModelDriven;

public class Chat extends Session implements ModelDriven<Room>
{
	private static final long serialVersionUID = 7405836330868983108L;

	private Room room;
	private String message;

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
			setRoom(Rooms.getRoomsImpl().get(_session.get(Rooms.ROOM_ID)));
			if (message != null && !message.trim().isEmpty())
			{
				Evaluator eval = new Evaluator();
				Message msg = new Message(now(), getUser(), eval.eval(message), getRoom());
				getRoom().getMessages().add(msg);
				Persistor<Room> db = new Persistor<Room>();
				db.saveOrUpdate(getRoom());
			}
		}
		return ret;
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
