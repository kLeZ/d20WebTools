package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Account;
import it.d4nguard.d20webtools.model.Room;

import java.util.LinkedHashMap;

public class RoomManager extends Session
{
	private static final long serialVersionUID = -2654452895755002089L;
	private Room room;
	private LinkedHashMap<Integer, Room> rooms = new LinkedHashMap<Integer, Room>();

	@Override
	public String execute() throws Exception
	{
		rooms.put(1, new Room(1, "Nebula", "julius8774@gmail.com"));
		rooms.put(2, new Room(2, "Stralis", "root@d4nguard.org"));
		rooms.put(3, new Room(3, "Ankiku", "piero@cicciamica.info"));
		rooms.put(4, new Room(4, "Mueller", "greg@google.com"));
		return SUCCESS;
	}

	public String newRoom() throws Exception
	{
		String ret = SUCCESS;
		if (getRoom().getName().contentEquals("Nebula"))
		{
			getRoom().setId(1);
			getRoom().setMaster(getAccount());
			getRoom().getMembers().add(new Account("root@d4nguard.org", ""));
			getRoom().getMembers().add(new Account("piero@cicciamica.info", ""));
			getRoom().getMembers().add(new Account("kiki@otaku4eva.net", ""));
			getRoom().getMembers().add(new Account("greg@google.com", ""));
		}
		else
		{
			ret = ERROR;
		}
		return ret;
	}

	public String joinRoom() throws Exception
	{
		String ret = execute();
		setRoom(getRooms().get(getRoom().getId()));
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

	public LinkedHashMap<Integer, Room> getRooms()
	{
		return rooms;
	}

	public void setRooms(LinkedHashMap<Integer, Room> rooms)
	{
		this.rooms = rooms;
	}
}
