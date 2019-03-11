package com.gam.urcap.programmonitor.installation;

import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import com.gam.urcap.programmonitor.daemon.DaemonXmlRpcCommunicator;
import com.gam.urcap.programmonitor.daemon.DaemonXmlRpcCommunicator.RESULT_TYPE;
import com.gam.urcap.programmonitor.daemon.ProgramMonitorDaemonService;
import com.gam.urcap.programmonitor.monitoring.ResultSet;
import com.ur.urcap.api.contribution.DaemonContribution.State;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class ProgramMonitorInstallationNodeContribution implements InstallationNodeContribution{

	private final InstallationAPIProvider apiProvider;
	private final ProgramMonitorInstallationNodeView view;
	private final ProgramMonitorDaemonService programMonitorDaemonService;
	private final DaemonXmlRpcCommunicator monitorCommunicator = new DaemonXmlRpcCommunicator();
	
	private static final String XMLRPC_HANDLE = "PROGRAM_MONITOR_DAEMON";
	
	private Timer updateTimer;
	
	private ResultSet RESULTS = new ResultSet();
	
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
		if(ENABLE_PROGRAM_MONITORING) {
			startTimer();
		}
		applyDaemonState(enabled);
	}
	
	@Override
	public void openView() {
		view.setMonitoringEnabledState(ENABLE_PROGRAM_MONITORING);
		view.setDaemonStatusLabel(getDaemonStatusLabel());
		if(ENABLE_PROGRAM_MONITORING) {
			startTimer();
		}
		if(resultsAvailable()) {
			RESULTS = getResults();
			view.setResultSet(RESULTS);
		}
	}

	@Override
	public void closeView() {
		stopTimer();
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		if(ENABLE_PROGRAM_MONITORING) {
			// Only write any script, if the enable-flag is set. 
			
			// Make an informational popup to the user
			writer.appendLine("popup(\"Program Monitor is active, and will monitor the execution of this program\",\"Program Monitor\",blocking=True)");
			
			// Create an XML-RPC handle
			writer.assign(XMLRPC_HANDLE, "rpc_factory(\"xmlrpc\",\"http://127.0.0.1:23444\")");
		}
	}
	
	private void startTimer() {
		updateTimer = new Timer(true);
		updateTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						view.setDaemonStatusLabel(getDaemonStatusLabel());
					}
				});
			}
		}, 0, 500);
	}
	
	private void stopTimer() {
		if(updateTimer!=null) {
			updateTimer.cancel();
		}
	}
	
	private void applyDaemonState(boolean state) {
		if(state) {
			this.programMonitorDaemonService.getDaemonContribution().start();
		} else {
			if(resultsAvailable()) {
				RESULTS = getResults();
			}
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
	
	private boolean resultsAvailable() {
//		return monitorCommunicator.hasMonitored();
		
		// Debugging
		return true;
	}
	
	private ResultSet getResults() {
		ResultSet results = new ResultSet();
//		results.setX(monitorCommunicator.getResult(RESULT_TYPE.X_MIN), monitorCommunicator.getResult(RESULT_TYPE.X_MAX));
//		results.setY(monitorCommunicator.getResult(RESULT_TYPE.Y_MIN), monitorCommunicator.getResult(RESULT_TYPE.Y_MAX));
//		results.setZ(monitorCommunicator.getResult(RESULT_TYPE.Z_MIN), monitorCommunicator.getResult(RESULT_TYPE.Z_MAX));
//		results.setSpeedMax(monitorCommunicator.getResult(RESULT_TYPE.SPEED_MAX));
		
		// Debugging
		results.setX(-200.0, 250.0);
		results.setY(-100.0, 540.6687);
		results.setZ(0.0, 248.777);
		results.setSpeedMax(1524.4);
		return results;
	}

}
