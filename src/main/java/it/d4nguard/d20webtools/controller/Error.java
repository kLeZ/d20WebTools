package it.d4nguard.d20webtools.controller;

import org.apache.log4j.Logger;

public class Error extends Session
{
	private static final long serialVersionUID = -3557769124301054972L;
	private static final Logger log = Logger.getLogger(Error.class);
	private Exception exception;

	@Override
	public String execute() throws Exception
	{
		log.error(ERROR, exception);
		System.out.println("Is exception null: " + (exception == null));
		System.out.println("" + exception.getMessage());
		return ERROR;
	}

	public Exception getException()
	{
		return exception;
	}

	public void setException(Exception exception)
	{
		this.exception = exception;
	}
}
