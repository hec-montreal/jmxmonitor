package ca.hec.jmxmonitor.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.hec.jmxmonitor.exception.MonitorException;

public class Log
{
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
	private String filename;
	private BufferedWriter writer;

	public Log(String filename) throws MonitorException
	{
		this.filename = filename;

		try
		{
			this.writer = new BufferedWriter(new FileWriter(filename, true));
		}
		catch (IOException e)
		{
			throw new MonitorException("Cannot open log file " + filename);
		}
	}

	public void info(String info) throws MonitorException
	{
		String line = "[" + DATE_FORMAT.format(new Date()) + "]   " + info;

		System.out.println(line);

		try
		{
			writer.write(line + "\n");
			writer.flush();
		}
		catch (IOException e)
		{
			throw new MonitorException("Cannot write to log file " + filename);
		}
	}

	public void error(MonitorException e)
	{
		try
		{
			info("MonitorException: " + e.getMessage());
		}
		catch (MonitorException e2)
		{
			System.out.println(e.getMessage());
			System.out.println(e2.getMessage());
		}
	}

	public void close() throws MonitorException
	{
		try
		{
			this.writer.close();
		}
		catch (IOException e)
		{
			throw new MonitorException("Cannot close log file " + filename);
		}
	}
}
