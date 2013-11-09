package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.common.Evaluator;
import it.d4nguard.d20webtools.common.TimeSpan;
import it.d4nguard.d20webtools.model.Member;
import it.d4nguard.d20webtools.model.Message;
import it.d4nguard.d20webtools.model.Room;

import java.util.Collection;
import java.util.Date;

import com.opensymphony.xwork2.ModelDriven;

public class Chat extends Session implements ModelDriven<Room>
{
	private static final long serialVersionUID = 7405836330868983108L;

	private Room room;
	private String message;

	@Override
	public String execute() throws Exception
	{
		String ret = SUCCESS;
		synchronized (_session)
		{
			removeSleepingMembers();
			setRoom(getPersistor().findById(Room.class, (Long) _session.get(ROOM_ID)));
			if (message != null && !message.trim().isEmpty())
			{
				Evaluator eval = new Evaluator();
				Message msg = new Message(TimeSpan.now(), getUser(), eval.eval(message), getRoom());
				getPersistor().save(msg);
				setRoom(getPersistor().findById(Room.class, getRoom().getId()));
			}
		}
		return ret;
	}

	private synchronized void removeSleepingMembers()
	{
		Collection<Message> messages = getPersistor().findByEqField(Message.class, "user.email", getUser().getEmail());
		Date d = null;
		for (Message message : messages)
			if (d == null) d = message.getTime();
			else if (d.after(message.getTime())) d = message.getTime();
		if (new TimeSpan(d).diff().getMinutes() > 30L)
		{
			Member m = new Member(getUser(), getRoom());
			getPersistor().delete(m);
		}
	}

	public synchronized Room getRoom()
	{
		Long id = 0L;
		if (room == null)
		{
			if (_session.get(ROOM_ID) != null) id = (Long) _session.get(ROOM_ID);
			else if (room != null && room.getId() != null) id = room.getId();
			if (id > 0L) room = getPersistor().findById(Room.class, id);
			else room = new Room();
		}
		if (room.getMembers().size() <= 0 && id > 0L) room.getMembers().addAll(getPersistor().findByEqField(Member.class, "room.id", id));
		return room;
	}

	public synchronized void setRoom(Room room)
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

	public Collection<Member> getMembers()
	{
		return getPersistor().findByEqField(Member.class, "room.id", _session.get(ROOM_ID));
	}

	@Override
	public Room getModel()
	{
		return getRoom();
	}
}
