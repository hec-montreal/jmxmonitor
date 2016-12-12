package ca.hec.jmxmonitor;

import java.util.TimerTask;

import javax.management.ObjectName;

import ca.hec.jmxmonitor.api.Client;
import ca.hec.jmxmonitor.exception.MonitorException;

public class HikariMonitorTask extends TimerTask
{
	private static final String HIKARI_QUERY = "com.zaxxer.hikari:type=pool ";

	private App app;
	private Client client;

	public HikariMonitorTask(App app)
	{
		this.app = app;
		this.client = app.getClient();
	}

	@Override
	public void run()
	{
		try
		{
			ObjectName name = client.findMBeanName(HIKARI_QUERY);
			int activeConnections = Integer.parseInt(client.getAttribute(name, "ActiveConnections").toString());
			int idleConnections = Integer.parseInt(client.getAttribute(name, "IdleConnections").toString());
			int threadsAwaitingConnection = Integer.parseInt(client.getAttribute(name, "ThreadsAwaitingConnection").toString());
			int totalConnections = Integer.parseInt(client.getAttribute(name, "TotalConnections").toString());

			app.log("" + activeConnections + ", " + idleConnections + ", " + threadsAwaitingConnection + ", " + totalConnections);
		}
		catch (MonitorException e)
		{
			app.log("Impossible to find Hikari MBean");
		}
	}
}
