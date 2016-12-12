package ca.hec.jmxmonitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import ca.hec.jmxmonitor.api.Client;
import ca.hec.jmxmonitor.client.ClientImpl;
import ca.hec.jmxmonitor.exception.MonitorException;

public class App
{
	private SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS");
	private Client client;
	private Timer timer;
	private long frequency;
	private String logPath;
	private BufferedWriter writer;

	private App(String[] args)
	{
		if (args.length < 4)
		{
			System.out.println("Usage: monitor [host] [port] [frequency] [logPath]");

			System.exit(0);
		}

		String host = args[0];
		String port = args[1];

		frequency = Long.parseLong(args[2]);
		logPath = args[3];

		log("Monitoring Hikari on " + host + ":" + port + " (frequency=" + frequency + "ms)");

		client = new ClientImpl("localhost", "6433");
	}

	void run() throws MonitorException
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(logPath, true));
		}
		catch (IOException e)
		{
			throw new MonitorException("Invalid log path: " + logPath);
		}

		client.open();

		timer = new Timer();
		timer.schedule(new HikariMonitorTask(this), 0, frequency);
	}

	public void stop()
	{
		try
		{
			writer.close();
		}
		catch (IOException e)
		{

		}

		try
		{
			client.close();
		}
		catch (MonitorException e)
		{
			e.printStackTrace();
		}

		timer.cancel();
	}

	public Client getClient()
	{
		return client;
	}

	public void log(String info)
	{
		String line = "[" + LOG_DATE_FORMAT.format(new Date()) + "]   " + info;

		if (writer != null)
		{
			try
			{
				writer.write(line + "\n");
				writer.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		System.out.println(line);
	}

	public static void main(String[] args) throws MonitorException
	{
		new App(args).run();
	}
}
