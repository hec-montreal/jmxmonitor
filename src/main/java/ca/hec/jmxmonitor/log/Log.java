package ca.hec.jmxmonitor.log;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import ca.hec.jmxmonitor.exception.MonitorException;

public class Log
{
	public Log(String filename) throws MonitorException
	{
		try
		{
			PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss.SSS} %m %n");
			DailyRollingFileAppender appender = new DailyRollingFileAppender(layout, filename, "'.'yyyy-MM-dd");
			appender.setName("jmxmonitor");
			appender.setAppend(true);
			appender.activateOptions();

			Logger.getRootLogger().addAppender(appender);
		}
		catch (Exception e)
		{
			throw new MonitorException(e);
		}
	}

	public void info(String message)
	{
		Logger.getRootLogger().info(message);
	}

	public void error(String message)
	{
		Logger.getRootLogger().error(message);
	}

	public void error(Exception e)
	{
		Logger.getRootLogger().error(e.getMessage());
	}
}
