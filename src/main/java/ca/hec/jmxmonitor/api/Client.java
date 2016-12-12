package ca.hec.jmxmonitor.api;

import java.util.Set;

import javax.management.MBeanInfo;
import javax.management.ObjectName;

import ca.hec.jmxmonitor.exception.MonitorException;

public interface Client
{
	String getUrl();

	void open() throws MonitorException;

	void close() throws MonitorException;

	String[] getMBeanDomains() throws MonitorException;

	Set<ObjectName> getAllMBeanNames() throws MonitorException;

	Set<ObjectName> findMBeanNames(String name) throws MonitorException;

	ObjectName findMBeanName(String name) throws MonitorException;

	boolean hasMBean(String name) throws MonitorException;

	MBeanInfo getMBeanInfo(ObjectName name) throws MonitorException;

	Object getAttribute(ObjectName name, String attributeName) throws MonitorException;
}
