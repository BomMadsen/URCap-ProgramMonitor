package com.gam.urcap.programmonitor.impl;

import com.gam.urcap.programmonitor.daemon.DaemonXmlRpcCommunicator;
import com.gam.urcap.programmonitor.daemon.DaemonXmlRpcCommunicator.RESULT_TYPE;

public class TestersChoice {

	/*****
	 * This is a test-class, used to validate designs throughout development. 
	 * The class has no meaning to the general URCap. 
	 * But it can be used to test various functionality without building and deploying the URCap.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DaemonXmlRpcCommunicator daemonTester = new DaemonXmlRpcCommunicator();
		
		System.out.println("Has monitored: "+daemonTester.hasMonitored());
		System.out.println("X_Min = "+daemonTester.getResult(RESULT_TYPE.X_MIN));
	}

}
