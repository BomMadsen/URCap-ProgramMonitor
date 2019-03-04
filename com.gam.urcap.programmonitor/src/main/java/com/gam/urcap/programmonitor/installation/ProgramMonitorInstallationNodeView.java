package com.gam.urcap.programmonitor.installation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;

public class ProgramMonitorInstallationNodeView implements SwingInstallationNodeView<ProgramMonitorInstallationNodeContribution>{

	public ProgramMonitorInstallationNodeView(ViewAPIProvider apiProvider) {
		
	}
	
	/*****
	 * General UI components
	 */
	private JTabbedPane MASTER_TABS = new JTabbedPane(JTabbedPane.TOP);
	private JPanel CONFIGURATION_PANEL = new JPanel();
	private JPanel RESULTS_PANEL = new JPanel();
	
	/*****
	 * Configuration panel UI components
	 */
	private JButton ENABLE_MONITORING_BUTTON = new JButton();
	private JButton DISABLE_MONITORING_BUTTON = new JButton();
	private JLabel DAEMON_STATUS_LABEL = new JLabel();
	
	@Override
	public void buildUI(JPanel panel, ProgramMonitorInstallationNodeContribution contribution) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(createDescriptionLabelBox("This is a sample URCap, that monitors the motion of the robot, when a program is executed."));
		panel.add(MASTER_TABS);
		
		MASTER_TABS.addTab("Configuration", CONFIGURATION_PANEL);
		MASTER_TABS.addTab("Results", RESULTS_PANEL);
		MASTER_TABS.setPreferredSize(panel.getSize());
		
		buildConfigurationTab(contribution);
		buildResultsTab(contribution);
	}
	
	public void setMonitoringEnabledState(boolean enabled) {
		ENABLE_MONITORING_BUTTON.setEnabled(!enabled);
		DISABLE_MONITORING_BUTTON.setEnabled(enabled);
	}
	
	public void setDaemonStatusLabel(String status) {
		DAEMON_STATUS_LABEL.setText(status);
	}
	
	private void buildConfigurationTab(ProgramMonitorInstallationNodeContribution contribution) {
		CONFIGURATION_PANEL.setLayout(new BoxLayout(CONFIGURATION_PANEL, BoxLayout.Y_AXIS));
		
		CONFIGURATION_PANEL.add(createVerticalSpacer(15));
		CONFIGURATION_PANEL.add(createDescriptionLabelBox("Select whether to monitor the program below: "));
		CONFIGURATION_PANEL.add(createVerticalSpacer(15));
		CONFIGURATION_PANEL.add(createEnableDisableButtonBox(ENABLE_MONITORING_BUTTON, DISABLE_MONITORING_BUTTON, 
				"Enable", "Disable", contribution));
		CONFIGURATION_PANEL.add(createVerticalSpacer(30));
		CONFIGURATION_PANEL.add(createStatusLabelBox("Monitoring status: ", DAEMON_STATUS_LABEL));
	}
	
	private void buildResultsTab(ProgramMonitorInstallationNodeContribution contribution) {
		
	}

	private Box createEnableDisableButtonBox(JButton enableButton, JButton disableButton, 
			String enableText, String disableText,
			final ProgramMonitorInstallationNodeContribution contribution) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		enableButton.setText(enableText);
		disableButton.setText(disableText);
		
		Dimension buttonSize = new Dimension(250, 100);
		
		enableButton.setPreferredSize(buttonSize);
		enableButton.setMaximumSize(buttonSize);
		
		disableButton.setPreferredSize(buttonSize);
		disableButton.setMaximumSize(buttonSize);
		
		enableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contribution.enableMonitoringChanged(true);
			}
		});
		
		disableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contribution.enableMonitoringChanged(false);
			}
		});
		
		box.add(enableButton);
		box.add(Box.createRigidArea(new Dimension(20, 0)));
		box.add(disableButton);
		
		return box;
	}
	
	private Box createStatusLabelBox(String description, JLabel status) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel descriptionLabel = new JLabel(description);
		descriptionLabel.setPreferredSize(new Dimension(200, 30));
		descriptionLabel.setMaximumSize(descriptionLabel.getPreferredSize());
		
		box.add(descriptionLabel);
		box.add(status);
		
		return box;
	}
	
	private Box createDescriptionLabelBox(String text) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel description = new JLabel(text);
		
		box.add(description);
		
		return box;
	}
	
	private Component createVerticalSpacer(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}
	
}
