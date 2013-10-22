/**
 * 
 */
package it.d4nguard.d20webtools.persistence;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

/**
 * @author kLeZ-hAcK
 */
public class StatefulWork implements Work
{
	private final String sql;
	private boolean workDone = false;
	private boolean stopOnError = true;

	public StatefulWork(final String sql)
	{
		this.sql = sql;
	}

	public String getSql()
	{
		return sql;
	}

	public boolean isWorkDone()
	{
		return workDone;
	}

	private void setWorkDone(final boolean workDone)
	{
		this.workDone = workDone;
	}

	public boolean isStopOnError()
	{
		return stopOnError;
	}

	public void setStopOnError(final boolean stopOnError)
	{
		this.stopOnError = stopOnError;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.jdbc.Work#execute(java.sql.Connection)
	 */
	@Override
	public void execute(final Connection arg0) throws SQLException
	{
		boolean ret = false;
		final ScriptRunner runner = new ScriptRunner(arg0, false, isStopOnError());
		try
		{
			ret = runner.runScript(new StringReader(sql));
		}
		catch (final IOException e)
		{
			ret = false;
			throw new SQLException(e);
		}
		finally
		{
			setWorkDone(ret);
		}
	}
}
