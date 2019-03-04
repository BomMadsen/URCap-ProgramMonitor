package com.gam.urcap.programmonitor.installation;

import com.gam.urcap.programmonitor.daemon.ProgramMonitorDaemonService;
import com.ur.urcap.api.contribution.DaemonContribution.State;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class ProgramMonitorInstallationNodeContribution implements InstallationNodeContribution{

	private final InstallationAPIProvider apiProvider;
	private final ProgramMonitorInstallationNodeView view;
	private final ProgramMonitorDaemonService programMonitorDaemonService;
	
	private static final String XMLRPC_HANDLE = "PROGRAM_MONITOR_DAEMON";
	
	/*****
	 * BEWARE! 
	 * This is an instance variable, and is not the typical approach for most URCaps. 
	 * This is expressly used, since the Program Monitor should only be active for testing purposes. 
	 * This setting is not persisted through reboot, reloading installations etc. 
	 * For a more typical approach, where the value is saved, use the DataModel.
	 * 
	 * This variable indicates if Program Monitoring is set active for the time being. 
	 */
	private boolean ENABLE_PROGRAM_MONITORING = false;
	
	public ProgramMonitorInstallationNodeContribution(InstallationAPIProvider apiProvider,
			ProgramMonitorInstallationNodeView view, 
			ProgramMonitorDaemonService programMonitorDaemon) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.programMonitorDaemonService = programMonitorDaemon;
	}
	
	public void enableMonitoringChanged(boolean enabled) {
		ENABLE_PROGRAM_MONITORING = enabled;
		view.setMonitoringEnabledState(ENABLE_PROGRAM_MONITORING);
	}
	
	@Override
	public void openView() {
		view.setMonitoringEnabledState(ENABLE_PROGRAM_MONITORING);
		view.setDaemonStatusLabel(getDaemonStatusLabel());
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		if(ENABLE_PROGRAM_MONITORING) {
			// Only write any script, if the enable-flag is set. 
			
			// Make an informational popup to the user
			writer.appendLine("popup(\"Program Monitor is active, and will monitor the execution of this program\",\"Program Monitor\",blocking=True)");
			
			// Create an XML-RPC handle
//			writer.assign(XMLRPC_HANDLE, "rpc_factory(\"xmlrpc\",\"http://127.0.0.1:23444\")");
		}
	}
	
	private void applyDaemonState(boolean state) {
		if(state) {
			this.programMonitorDaemonService.getDaemonContribution().start();
		} else {
			this.programMonitorDaemonService.getDaemonContribution().stop();
		}
	}
	
	private boolean isDaemonRunning() {
		return this.programMonitorDaemonService.getDaemonContribution().getState().equals(State.RUNNING);
	}
	
	private String getDaemonStatusLabel() {
		if(ENABLE_PROGRAM_MONITORING) {
			if(isDaemonRunning()) {
				return "Ready to monitor";
			} else {
				return "Initializing...";
			}
		} else {
			return "Monitoring not activated";
		}
	}

}
