package ca.hec.jmxmonitor;

public class HikariStateSnapshot
{
	private int activeConnections;
	private int idleConnections;
	private int threadsAwaitingConnection;
	private int totalConnections;

	public HikariStateSnapshot()
	{

	}

	@Override
	public String toString()
	{
		return "" + activeConnections + ", " + idleConnections + ", " + threadsAwaitingConnection + ", " + totalConnections;
	}

	public int getActiveConnections()
	{
		return activeConnections;
	}

	public void setActiveConnections(int activeConnections)
	{
		this.activeConnections = activeConnections;
	}

	public int getIdleConnections()
	{
		return idleConnections;
	}

	public void setIdleConnections(int idleConnections)
	{
		this.idleConnections = idleConnections;
	}

	public int getThreadsAwaitingConnection()
	{
		return threadsAwaitingConnection;
	}

	public void setThreadsAwaitingConnection(int threadsAwaitingConnection)
	{
		this.threadsAwaitingConnection = threadsAwaitingConnection;
	}

	public int getTotalConnections()
	{
		return totalConnections;
	}

	public void setTotalConnections(int totalConnections)
	{
		this.totalConnections = totalConnections;
	}
}
