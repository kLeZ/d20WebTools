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
				setRoom(r);
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
			Room r = getRoom();
			List<Member> members = getPersistor().findByEqField(Member.class, "room.id", r.getId());
			for (Member m : members)
				if (m.getUser().getEmail().contentEquals(getUser().getEmail())) getPersistor().delete(m);
			getPersistor().save(new Member(getUser(), r));
			_session.put(ROOM_ID, r.getId());
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
		Long id;
		if (room == null)
		{
			if (_session.get(ROOM_ID) != null) id = (Long) _session.get(ROOM_ID);
			else if (room != null && room.getId() != null) id = room.getId();
			else id = 0L;
			if (id > 0L) room = getPersistor().findById(Room.class, id);
			else room = new Room();
		}
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
		if (room != null && !room.getName().isEmpty() && room.getMaster() != null) getPersistor().saveOrUpdate(room);
	}

	public Collection<Room> getRooms()
	{
		return getPersistor().findAll(Room.class);
	}
}