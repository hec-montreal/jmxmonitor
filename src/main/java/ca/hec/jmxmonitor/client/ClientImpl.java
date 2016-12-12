package ca.hec.jmxmonitor.client;

import java.util.HashSet;
import java.util.Set;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import ca.hec.jmxmonitor.exception.MonitorException;

public class ClientImpl implements ca.hec.jmxmonitor.api.Client
{
	private String url;
	private JMXConnector jmxConnector;
	private MBeanServerConnection mbeanServerConnection;

	public ClientImpl(String host, String port)
	{
		this.url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
	}

	public String getUrl()
	{
		return this.url;
	}

	public void open() throws MonitorException
	{
		try
		{
			jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(url));

			mbeanServerConnection = jmxConnector.getMBeanServerConnection();
		}
		catch (Exception e)
		{
			throw new MonitorException(e);
		}
	}

	public void close() throws MonitorException
	{
		try
		{
			jmxConnector.close();
		}
		catch (Exception e)
		{
			throw new MonitorException(e);
		}
	}

	public String[] getMBeanDomains() throws MonitorException
	{
		try
		{
			return mbeanServerConnection.getDomains();
		}
		catch (Exception e)
		{
			throw new MonitorException(e);
		}
	}

	public Set<ObjectName> getAllMBeanNames() throws MonitorException
	{
		try
		{
			return mbeanServerConnection.queryNames(null, null);
		}
		catch (Exception e)
		{
			throw new MonitorException(e);
		}
	}

	public Set<ObjectName> findMBeanNames(String name) throws MonitorException
	{
		Set<ObjectName> ret = new HashSet<ObjectName>();

		for (ObjectName on : getAllMBeanNames())
		{
			if (on.getCanonicalName().toLowerCase().contains(name))
			{
				ret.add(on);
			}
		}

		return ret;
	}

	public ObjectName findMBeanName(String name) throws MonitorException
	{
		for (ObjectName on : getAllMBeanNames())
		{
			if (on.getCanonicalName().toLowerCase().contains(name))
			{
				return on;
			}
		}

		throw new MonitorException("Cannot find MBean named " + name);
	}

	public boolean hasMBean(String name) throws MonitorException
	{
		for (ObjectName on : getAllMBeanNames())
		{
			if (on.equals(name))
			{
				return true;
			}
		}

		return false;
	}

	public MBeanInfo getMBeanInfo(ObjectName name) throws MonitorException
	{
		try
		{
			return mbeanServerConnection.getMBeanInfo(name);
		}
		catch (Exception e)
		{
			throw new MonitorException(e);
		}
	}

	public Object getAttribute(ObjectName name, String attributeName) throws MonitorException
	{
		try
		{
			return mbeanServerConnection.getAttribute(name, attributeName);
		}
		catch (Exception e)
		{
			throw new MonitorException(e);
		}
	}
}
