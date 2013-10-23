package it.d4nguard.d20webtools.model;

public class Member
{
	private Long id;
	private User user;
	private Room room;

	public Member()
	{
	}

	public Member(Long id, User user, Room room)
	{
		super();
		this.id = id;
		this.user = user;
		this.room = room;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
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
