package it.d4nguard.d20webtools.controller;

public class SignIn extends Session
{
	private static final long serialVersionUID = 6686337047561791387L;

	@Override
	public String execute() throws Exception
	{
		String ret = "";
		if (!getUser().getEmail().trim().isEmpty())
		{
			if (getUser().getEmail().contentEquals("test@d20webtools.org") && getUser().getPassword().contentEquals("test"))
			{
				setLogged(true);
				ret = SUCCESS;
			}
			else
			{
				addActionError("User not registered. Please register!");
				setLogged(false);
				ret = ERROR;
			}
		}
		else
		{
			setLogged(false);
			ret = LOGIN;
		}
		return ret;
	}

	public String logout() throws Exception
	{
		setLogged(false);
		return SUCCESS;
	}
}
