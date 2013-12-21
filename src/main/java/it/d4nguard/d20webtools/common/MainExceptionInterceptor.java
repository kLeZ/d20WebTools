package it.d4nguard.d20webtools.common;

import it.d4nguard.d20webtools.persistence.Persistor;
import it.d4nguard.d20webtools.persistence.PersistorException;

import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.GenericJDBCException;

import com.mchange.v2.resourcepool.CannotAcquireResourceException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;
import com.opensymphony.xwork2.util.logging.Logger;

public class MainExceptionInterceptor extends ExceptionMappingInterceptor implements Constants
{
	private static final long serialVersionUID = 1047402329230864566L;

	@Override
	protected void doLog(Logger logger, Exception e)
	{
		super.doLog(logger, e);
	}

	@Override
	protected void publishException(ActionInvocation invocation, ExceptionHolder exceptionHolder)
	{
		System.out.println(exceptionHolder.getException().toString());
		System.out.println(exceptionHolder.getException().getMessage());
		System.out.println(exceptionHolder.getExceptionStack());
		if (exceptionHolder.getException() instanceof PersistorException && exceptionHolder.getException().getCause() instanceof GenericJDBCException && exceptionHolder.getException().getCause().getCause() instanceof SQLException && exceptionHolder.getException().getCause().getCause().getCause() instanceof CannotAcquireResourceException)
		{
			((Persistor) ServletActionContext.getServletContext().getAttribute(ENTITY_MANAGER)).destroy();
			ServletActionContext.getServletContext().setAttribute(ENTITY_MANAGER, null);
		}
		MainExceptionHolder holder = new MainExceptionHolder(exceptionHolder);
		super.publishException(invocation, holder);
	}
}
