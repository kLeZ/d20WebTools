package it.d4nguard.d20webtools.model;


public class Credential
{
	private String email;
	private String password;

	public String getWebEmail()
	{
		String temp = email.replace("@", " [@] ");
		return temp.substring(0, temp.lastIndexOf('.')).concat(" [.] ").concat(temp.substring(temp.lastIndexOf('.') + 1));
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
}
