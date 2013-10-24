package it.d4nguard.d20webtools.model;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = 4109094925530070042L;

	private Long id;
	private String name;
	private String email;
	private String password;

	/**
	 * Needed for struts2 to instantiate then populate, which is the default
	 * behavior of the framework.
	 */
	public User()
	{
	}

	public User(String name, String email, String password)
	{
		this.email = email;
		this.password = password;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getWebEmail()
	{
		return antiPhishing(email);
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public static String antiPhishing(String email)
	{
		String temp = email.replace("@", " [@] ");
		return temp.substring(0, temp.lastIndexOf('.')).concat(" [.] ").concat(temp.substring(temp.lastIndexOf('.') + 1));
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
}
