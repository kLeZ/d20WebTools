package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Account;
import it.d4nguard.d20webtools.model.Room;

import java.util.LinkedHashMap;

public class RoomManager extends Session
{
	public static final String ROOM_ID = "roomId";
	private static final long serialVersionUID = -2654452895755002089L;
	private Room room;
	public static final LinkedHashMap<Integer, Room> ROOMS = new LinkedHashMap<Integer, Room>();
	static
	{
		Room r = new Room(1, "Nebula", "julius8774@gmail.com");
		r.getMembers().add(new Account("root@d4nguard.org", ""));
		r.getMembers().add(new Account("piero@cicciamica.info", ""));
		r.getMembers().add(new Account("kiki@otaku4eva.net", ""));
		r.getMembers().add(new Account("greg@google.com", ""));
		ROOMS.put(1, r);
		ROOMS.put(2, new Room(2, "Stralis", "root@d4nguard.org"));
		ROOMS.put(3, new Room(3, "Ankiku", "piero@cicciamica.info"));
		ROOMS.put(4, new Room(4, "Mueller", "greg@google.com"));
	}

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
			if (getRoom().getName().contentEquals("Nebula"))
			{
				setRoom(ROOMS.get(1));
				getRoom().setMaster(getAccount());
				setRoom(getRoom());
			}
			else
			{
				ret = ERROR;
			}
		}
		return ret;
	}

	public String joinRoom() throws Exception
	{
		String ret = SUCCESS;
		setRoom(ROOMS.get(getRoom().getId()));
		_session.put(ROOM_ID, getRoom().getId());
		return ret;
	}

	public Room getRoom()
	{
		if (room == null) room = (Room) _session.get("room");
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
		if (room != null) _session.put("room", room);
		else _session.remove("room");
	}

	public LinkedHashMap<Integer, Room> getRooms()
	{
		return ROOMS;
	}
}
