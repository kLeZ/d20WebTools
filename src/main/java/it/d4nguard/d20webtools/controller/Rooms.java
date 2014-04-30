package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.common.StringUtils;
import it.d4nguard.d20webtools.model.Member;
import it.d4nguard.d20webtools.model.Room;
import it.d4nguard.d20webtools.persistence.Persistor;
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
		return super.execute();
	}

	public String newRoom() throws Exception
	{
		String ret = super.execute();
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
				addActionError(e.getMessage());
				addActionError("Room is already present. Please join the room, instead.");
				ret = EXCEPTION;
			}
		}
		return ret;
	}

	public String joinRoom() throws Exception
	{
		String ret = super.execute();
		Persistor persistor = getPersistor().manualFlush();
		try
		{
			_session.remove(SESSION_ROOM_ID);
			Room r = getRoom();
			List<Member> members = persistor.findByEqField(Member.class, "user.id", getUser().getId());
			for (Member m : members)
				persistor.delete(m);
			Member member = new Member(getUser(), getRoom());
			r.getMembers().add(member);
			persistor.save(member);
			getRoom();
			_session.put(SESSION_ROOM_ID, r.getId());
		}
		catch (PersistorException e)
		{
			addActionError(e.getMessage());
			ret = EXCEPTION;
		}
		persistor.automaticFlush().flush();
		return ret;
	}

	public String exit() throws Exception
	{
		String ret = super.execute();
		Persistor persistor = getPersistor().manualFlush();
		try
		{
			_session.remove(SESSION_ROOM_ID);
			List<Member> members = persistor.findByEqField(Member.class, "user.id", getUser().getId());
			for (Member m : members)
				persistor.delete(m);
		}
		catch (PersistorException e)
		{
			addActionError(e.getMessage());
			ret = EXCEPTION;
		}
		persistor.automaticFlush().flush();
		return ret;
	}

	public synchronized Room getRoom()
	{
		Persistor persistor = getPersistor().manualFlush();
		Long id = 0L;
		if (room == null || room.getId() == null)
		{
			if (_session.get(SESSION_ROOM_ID) != null) id = (Long) _session.get(SESSION_ROOM_ID);
			else if (room != null && room.getId() != null) id = room.getId();
			if (id > 0L) room = persistor.findById(Room.class, id);
			else if (room == null) room = new Room();
			else
			{
				if (StringUtils.isNullOrWhitespace(room.getName())) room = new Room();
				else
				{
					List<Room> found = persistor.findByEqField(Room.class, "name", room.getName());
					if (found != null && !found.isEmpty()) room = found.get(0);
				}
			}
		}
		if (room.getMembers().size() <= 0 && id > 0L) room.getMembers().addAll(persistor.findByEqField(Member.class, "room.id", id));
		persistor.automaticFlush().flush();
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
