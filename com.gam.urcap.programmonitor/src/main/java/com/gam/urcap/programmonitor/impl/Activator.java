package com.gam.urcap.programmonitor.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.gam.urcap.programmonitor.daemon.ProgramMonitorDaemonService;
import com.gam.urcap.programmonitor.installation.ProgramMonitorInstallationNodeService;
import com.ur.urcap.api.contribution.DaemonService;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;

/**
 * Hello world activator for the OSGi bundle URCAPS contribution
 *
 */
public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// First we create an instance of the DaemonService
		ProgramMonitorDaemonService programMonitorDaemon = new ProgramMonitorDaemonService();
		// Then we register the service to our BundleContext
		bundleContext.registerService(DaemonService.class, programMonitorDaemon, null);
		
		// Then we register the installation node
		// But the installation node needs a reference to our daemon service
		bundleContext.registerService(SwingInstallationNodeService.class, new ProgramMonitorInstallationNodeService(programMonitorDaemon), null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
	}
}

