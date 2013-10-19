package it.d4nguard.d20webtools.controller;

import it.d4nguard.d20webtools.model.Credential;
import it.d4nguard.d20webtools.model.Room;

import java.util.LinkedHashSet;

public class RoomManager extends Session
{
	private static final long serialVersionUID = -2654452895755002089L;
	private Room room;

	public String newRoom() throws Exception
	{
		String ret = SUCCESS;
		if (getRoom().getName().contentEquals("Nebula"))
		{
			getRoom().setMaster(getCredential());
			getRoom().setMembers(new LinkedHashSet<Credential>());
			getRoom().getMembers().add(new Credential("root@d4nguard.org", ""));
			getRoom().getMembers().add(new Credential("piero@cicciamica.info", ""));
			getRoom().getMembers().add(new Credential("kiki@otaku4eva.net", ""));
			getRoom().getMembers().add(new Credential("greg@google.com", ""));
		}
		else
		{
			ret = ERROR;
		}
		return ret;
	}

	public String joinRoom() throws Exception
	{
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
}
