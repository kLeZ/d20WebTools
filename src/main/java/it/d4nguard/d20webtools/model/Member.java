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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (room == null ? 0 : room.hashCode());
		result = prime * result + (user == null ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Member)) return false;
		Member other = (Member) obj;
		if (room == null)
		{
			if (other.room != null) return false;
		}
		else if (!room.equals(other.room)) return false;
		if (user == null)
		{
			if (other.user != null) return false;
		}
		else if (!user.equals(other.user)) return false;
		return true;
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
		builder.append(room.getName());
		builder.append("]");
		return builder.toString();
	}
}
