package it.d4nguard.d20webtools.model;

public class Member
{
	private Long id;
	private User user;
	private Room room;

	/**
	 * Needed for struts2 to instantiate then populate, which is the default
	 * behavior of the framework.
	 */
	public Member()
	{
	}

	public Member(User user, Room room)
	{
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

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Member [id=");
		builder.append(id);
		builder.append(", user=");
		builder.append(user);
		builder.append(", room=");
		builder.append(room);
		builder.append("]");
		return builder.toString();
	}
}
