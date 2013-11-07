package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Member;
import it.d4nguard.d20webtools.model.Room;
import it.d4nguard.d20webtools.persistence.PersistorException;

import java.util.Collection;
import java.util.List;

public class Rooms extends Session
{
	private static final long serialVersionUID = -2654452895755002089L;
	private Room room;

	@Override
	public String execute() throws Exception
	{
		return SUCCESS;
	}

	public String newRoom() throws Exception
	{
		String ret = SUCCESS;
		synchronized (_session)
		{
			try
			{
				Room r = getRoom();
				r.setMaster(getUser());
				getPersistor().save(r);
				ret = joinRoom();
			}
			catch (PersistorException e)
			{
				addActionError(e.getLocalizedMessage());
				addActionError("Room is already present. Please join the room, instead.");
				ret = EXCEPTION;
			}
		}
		return ret;
	}

	public String joinRoom() throws Exception
	{
		String ret = SUCCESS;
		try
		{
			_session.remove(ROOM_ID);
			Room r = getRoom();
			List<Member> members = getPersistor().findByEqField(Member.class, "user.id", getUser().getId());
			for (Member m : members)
				getPersistor().delete(m);
			Member member = new Member(getUser(), getRoom());
			r.getMembers().add(member);
			getPersistor().save(member);
			getRoom();
			_session.put(ROOM_ID, r.getId());
		}
		catch (PersistorException e)
		{
			addActionError(e.getLocalizedMessage());
			ret = EXCEPTION;
		}
		return ret;
	}

	public String exit() throws Exception
	{
		String ret = SUCCESS;
		try
		{
			_session.remove(ROOM_ID);
			List<Member> members = getPersistor().findByEqField(Member.class, "user.id", getUser().getId());
			for (Member m : members)
				getPersistor().delete(m);
		}
		catch (PersistorException e)
		{
			addActionError(e.getLocalizedMessage());
			ret = EXCEPTION;
		}
		return ret;
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
		if (room.getMembers().size() <= 0 && id > 0L)
		{
			room.getMembers().addAll(getPersistor().findByEqField(Member.class, "room.id", id));
		}
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public Collection<Room> getRooms()
	{
		return getPersistor().findAll(Room.class);
	}
}
