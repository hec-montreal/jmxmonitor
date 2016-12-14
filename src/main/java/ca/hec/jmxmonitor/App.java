package ca.hec.jmxmonitor;

import java.util.Timer;
import java.util.TimerTask;

import javax.management.ObjectName;

import ca.hec.jmxmonitor.client.Client;
import ca.hec.jmxmonitor.exception.MonitorException;
import ca.hec.jmxmonitor.log.Log;

public class App
{
	private String[] args;

	private Client client;
	private Log log;

	private Timer timer;
	private long frequency;

	private App(String[] args) throws MonitorException
	{
		this.args = args;

		String host = arg(0);

		if (host == null || host.length() == 0)
		{
			System.out.println("Host not specified, using localhost");

			host = "localhost";
		}

		String port = arg(1);

		if (port == null || port.length() == 0)
		{
			System.out.println("Port not specified, using 6433");

			port = "6433";
		}

		try
		{
			frequency = Long.parseLong(arg(2));
		}
		catch (Exception e)
		{
			System.out.println("Frequency not specified, using 500ms");

			frequency = 500;
		}

		String logFilename = arg(3);

		if (logFilename == null || logFilename.length() == 0)
		{
			System.out.println("Log file not specified, using ./jmxmonitor.log");

			logFilename = "./jmxmonitor.log";
		}

		client = new Client(host, port);

		log = new Log(logFilename);

		log.info("Monitoring Hikari on " + host + ":" + port + " (frequency=" + frequency + "ms, logfile='" + logFilename + "')");
	}

	private void run() throws MonitorException
	{
		client.open();

		timer = new Timer();

		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				try
				{
					HikariStateSnapshot snapshot = takeHikariStateSnapshot();

					log.info(snapshot.toString());
				}
				catch (MonitorException e)
				{
					log.error(e);
				}
			}
		}, 0, frequency);
	}

	private HikariStateSnapshot takeHikariStateSnapshot() throws MonitorException
	{
		HikariStateSnapshot ret = new HikariStateSnapshot();
		ObjectName hikariObjectName = client.findBeanName("com.zaxxer.hikari:type=pool ");

		ret.setActiveConnections(client.getAttributeInt(hikariObjectName, "ActiveConnections"));
		ret.setIdleConnections(client.getAttributeInt(hikariObjectName, "IdleConnections"));
		ret.setThreadsAwaitingConnection(client.getAttributeInt(hikariObjectName, "ThreadsAwaitingConnection"));
		ret.setTotalConnections(client.getAttributeInt(hikariObjectName, "TotalConnections"));

		return ret;
	}

	private static void die(String msg)
	{
		System.out.println(msg);

		System.exit(0);
	}

	private static void die(Exception e)
	{
		die(e.getMessage());
	}

	private String arg(int index)
	{
		if (index < args.length)
		{
			return args[index];
		}

		return null;
	}

	public static void main(String[] args)
	{
		try
		{
			new App(args).run();
		}
		catch (MonitorException e)
		{
			die(e);
		}
	}
}
