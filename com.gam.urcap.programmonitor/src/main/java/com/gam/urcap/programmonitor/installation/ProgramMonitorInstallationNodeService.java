package com.gam.urcap.programmonitor.installation;

import java.util.Locale;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.ContributionConfiguration;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class ProgramMonitorInstallationNodeService implements SwingInstallationNodeService<ProgramMonitorInstallationNodeContribution, ProgramMonitorInstallationNodeView>{

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		// No configuration is necessary here
	}

	@Override
	public String getTitle(Locale locale) {
		return "Program Monitor";
	}

	@Override
	public ProgramMonitorInstallationNodeView createView(ViewAPIProvider apiProvider) {
		return new ProgramMonitorInstallationNodeView(apiProvider);
	}

	@Override
	public ProgramMonitorInstallationNodeContribution createInstallationNode(InstallationAPIProvider apiProvider,
			ProgramMonitorInstallationNodeView view, DataModel model, CreationContext context) {
		return new ProgramMonitorInstallationNodeContribution(apiProvider, view);
	}

}