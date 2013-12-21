package it.d4nguard.d20webtools.common;

import com.opensymphony.xwork2.interceptor.ExceptionHolder;

public class MainExceptionHolder extends ExceptionHolder
{
	private static final long serialVersionUID = 8797156133329310575L;

	public MainExceptionHolder(ExceptionHolder exceptionHolder)
	{
		super(exceptionHolder.getException());
	}
}
