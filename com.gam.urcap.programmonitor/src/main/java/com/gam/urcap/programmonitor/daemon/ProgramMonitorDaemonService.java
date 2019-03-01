package com.gam.urcap.programmonitor.daemon;

import java.net.MalformedURLException;
import java.net.URL;

import com.ur.urcap.api.contribution.DaemonContribution;
import com.ur.urcap.api.contribution.DaemonService;

public class ProgramMonitorDaemonService implements DaemonService {

	private DaemonContribution daemonContribution;
	
	@Override
	public void init(DaemonContribution daemon) {
		this.daemonContribution = daemon;
		try {
			daemon.installResource(new URL("file:daemon/programMonitor/"));
		} catch (MalformedURLException e) {	}
	}

	@Override
	public URL getExecutable() {
		try {
			return new URL("file:daemon/programMonitor/programMonitor.py");	
		} catch (MalformedURLException e) {
			System.out.println("Error loading daemon, returning null");
			return null;
		}
	}

	public DaemonContribution getDaemonContribution() {
		return this.daemonContribution;
	}
	
}
