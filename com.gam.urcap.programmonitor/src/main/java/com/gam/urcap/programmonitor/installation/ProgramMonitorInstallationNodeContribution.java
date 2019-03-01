package com.gam.urcap.programmonitor.installation;

import com.gam.urcap.programmonitor.daemon.ProgramMonitorDaemonService;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class ProgramMonitorInstallationNodeContribution implements InstallationNodeContribution{

	private final InstallationAPIProvider apiProvider;
	private final ProgramMonitorInstallationNodeView view;
	private final ProgramMonitorDaemonService programMonitorDaemonService;
	
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
	
	@Override
	public void openView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		// TODO Auto-generated method stub
		
	}

}
