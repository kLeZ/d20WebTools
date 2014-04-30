package it.d4nguard.d20webtools.common;

import java.util.Calendar;
import java.util.Date;

public class TimeSpan
{
	private final Date d1;
	private final Date d2;

	private long milliseconds = 0L;
	private long seconds = 0L;
	private long minutes = 0L;
	private long hours = 0L;
	private long days = 0L;

	public TimeSpan(Date d)
	{
		this(d, true);
	}

	public TimeSpan(Date d, boolean last)
	{
		this(last ? now() : d, last ? d : now());
	}

	public TimeSpan(Date d1, Date d2)
	{
		this.d1 = d1;
		this.d2 = d2;
	}

	public TimeSpan diff()
	{
		if (d1 != null && d2 != null)
		{
			milliseconds = d1.getTime() - d2.getTime();
		}

		seconds = milliseconds / 1000 % 60;
		minutes = milliseconds / (60 * 1000) % 60;
		hours = milliseconds / (60 * 60 * 1000) % 24;
		days = milliseconds / (24 * 60 * 60 * 1000);
		return this;
	}

	public Date getD1()
	{
		return d1;
	}

	public Date getD2()
	{
		return d2;
	}

	public long getMilliseconds()
	{
		return milliseconds;
	}

	public long getSeconds()
	{
		return seconds;
	}

	public long getMinutes()
	{
		return minutes;
	}

	public long getHours()
	{
		return hours;
	}

	public long getDays()
	{
		return days;
	}

	public static Date now()
	{
		return Calendar.getInstance().getTime();
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("TimeSpan [d1=");
		builder.append(d1);
		builder.append(", d2=");
		builder.append(d2);
		builder.append(", milliseconds=");
		builder.append(milliseconds);
		builder.append(", seconds=");
		builder.append(seconds);
		builder.append(", minutes=");
		builder.append(minutes);
		builder.append(", hours=");
		builder.append(hours);
		builder.append(", days=");
		builder.append(days);
		builder.append("]");
		return builder.toString();
	}
}
