package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.common.Evaluator;
import it.d4nguard.d20webtools.common.TimeSpan;
import it.d4nguard.d20webtools.model.Member;
import it.d4nguard.d20webtools.model.Message;
import it.d4nguard.d20webtools.model.Room;
import it.d4nguard.d20webtools.persistence.Persistor;

import java.util.Date;
import java.util.List;

public class Chat extends Session
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

			Persistor<Room> db = new Persistor<Room>(getHibernateFactory());
			setRoom(db.findById(Room.class, (Long) _session.get(ROOM_ID)));
			if (message != null && !message.trim().isEmpty())
			{
				Evaluator eval = new Evaluator();
				Message msg = new Message(TimeSpan.now(), getUser(), eval.eval(message), getRoom());
				Persistor<Message> db_m = new Persistor<Message>(getHibernateFactory());
				db_m.save(msg);
				setRoom(db.findById(Room.class, getRoom().getId()));
			}
		}
		return ret;
	}

	private void removeSleepingMembers()
	{
		Persistor<Message> db_m = new Persistor<Message>(getHibernateFactory());

		List<Message> messages = db_m.findByEqField(Message.class, "user.email", getUser().getEmail());
		Date d = null;
		for (Message message : messages)
		{
			if (d == null) d = message.getTime();
			else if (d.after(message.getTime())) d = message.getTime();
		}
		if (new TimeSpan(d).diff().getMinutes() > 30L)
		{
			Persistor<Member> db = new Persistor<Member>(getHibernateFactory());
			Member m = new Member(getUser(), getRoom());
			db.delete(m);
		}
	}

	public Room getRoom()
	{
		if (room == null)
		{
			Persistor<Room> db = new Persistor<Room>(getHibernateFactory());
			setRoom(db.findById(Room.class, (Long) _session.get(ROOM_ID)));
		}
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
}
