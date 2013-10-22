package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Room;
import it.d4nguard.d20webtools.persistence.Persistor;
import it.d4nguard.d20webtools.persistence.PersistorException;

import java.util.LinkedHashMap;
import java.util.List;

public class Rooms extends Session
{
	public static final String SESSION_ROOM_NAME = "room";
	public static final String ROOM_ID = "roomId";
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
			Persistor<Room> db = new Persistor<Room>();
			try
			{
				getRoom().setMaster(getUser());
				setRoom(getRoom());
				db.save(getRoom());
			}
			catch (PersistorException e)
			{
				addActionError(e.getLocalizedMessage());
				addActionError("Room is already present. Please join the room, instead.");
				ret = ERROR;
			}
		}
		return ret;
	}

	public String joinRoom() throws Exception
	{
		String ret = SUCCESS;
		Persistor<Room> db = new Persistor<Room>();
		try
		{
			setRoom(db.findById(Room.class, new Long(getRoom().getId())));
			_session.put(ROOM_ID, getRoom().getId());
		}
		catch (PersistorException e)
		{
			addActionError(e.getLocalizedMessage());
			ret = ERROR;
		}
		return ret;
	}

	public Room getRoom()
	{
		if (room == null) room = (Room) _session.get(SESSION_ROOM_NAME);
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
		if (room != null) _session.put(SESSION_ROOM_NAME, room);
		else _session.remove(SESSION_ROOM_NAME);
	}

	public LinkedHashMap<Integer, Room> getRooms()
	{
		return getRoomsImpl();
	}

	public static LinkedHashMap<Integer, Room> getRoomsImpl()
	{
		LinkedHashMap<Integer, Room> ret = new LinkedHashMap<Integer, Room>();
		Persistor<Room> db = new Persistor<Room>();
		List<Room> rooms = db.findAll(Room.class);
		for (Room r : rooms)
			ret.put(r.getId(), r);
		return ret;
	}
}
