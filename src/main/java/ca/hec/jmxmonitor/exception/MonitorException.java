package ca.hec.jmxmonitor.exception;

public class MonitorException extends Exception
{
	private static final long serialVersionUID = 1L;

	public MonitorException(Exception e)
	{
		super(e);
	}

	public MonitorException(String message)
	{
		super(message);
	}
}
