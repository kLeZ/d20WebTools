package it.d4nguard.d20webtools.controller;

public class SignIn extends Session
{
	private static final long serialVersionUID = 6686337047561791387L;

	@Override
	public String execute() throws Exception
	{
		String ret = "";
		if (getCredential() != null)
		{
			if (getCredential().getEmail().contentEquals("julius8774@gmail.com") && getCredential().getPassword().contentEquals("klez-hack87"))
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
