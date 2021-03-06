package com.gam.urcap.programmonitor.daemon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class DaemonXmlRpcCommunicator {

	private final XmlRpcClient client;
	
	private static final String HOST = "127.0.0.1";
	private static final int PORT_DEFAULT = 23444;
	
	public enum RESULT_TYPE{
		X_MAX("xmax"),
		X_MIN("xmin"),
		Y_MAX("ymax"),
		Y_MIN("ymin"),
		Z_MAX("zmax"),
		Z_MIN("zmin"),
		SPEED_MAX("speedmax");
		
		private String id;
		RESULT_TYPE(String id){
			this.id = id;
		}
		String getID() {
			return this.id;
		}
	}
	
	public DaemonXmlRpcCommunicator(int port) {
		this.client = createXmlRpcClient(HOST, port);
	}
	
	public DaemonXmlRpcCommunicator() {
		this.client = createXmlRpcClient(HOST, PORT_DEFAULT);
	}
	
	private XmlRpcClient createXmlRpcClient(String host, int port) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setEnabledForExtensions(true);
		try {
			config.setServerURL(new URL("http://" + host + ":" + port));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		config.setConnectionTimeout(1000); //1s
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		return client;
	}
	
	public double getResult(RESULT_TYPE type) {
		ArrayList<String> args = new ArrayList<String>();
		args.add(type.getID());
		double result = 0;
		try {
			Object res = client.execute("getResult", args);
			if(res instanceof Double) {
				result = (Double) res;
			}
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception in getResult");
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean hasMonitored() {
		boolean monitored = false;
		try {
			Object result = client.execute("hasMonitored", new ArrayList<String>());
			if(result instanceof Boolean) {
				monitored = (Boolean) result;
			}
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
			e.printStackTrace();
		}
		return monitored;
	}
	
}
