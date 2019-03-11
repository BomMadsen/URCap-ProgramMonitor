package com.gam.urcap.programmonitor.installation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import com.gam.urcap.programmonitor.monitoring.AxisExtremity;
import com.gam.urcap.programmonitor.monitoring.ResultSet;
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
	
	/*****
	 * Results panel UI components
	 */
	private JPanel RESULTS_DATA_PANEL = new JPanel();
	
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
	
	public void setResultSet(ResultSet resultSet) {
		if(resultSet.isResolved()) {
			RESULTS_PANEL.remove(RESULTS_DATA_PANEL);
			RESULTS_DATA_PANEL = createResultsDataPanel(resultSet);
			RESULTS_PANEL.add(RESULTS_DATA_PANEL);
		}
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
		RESULTS_PANEL.setLayout(new BoxLayout(RESULTS_PANEL, BoxLayout.Y_AXIS));
		
		RESULTS_PANEL.add(createVerticalSpacer(15));
		RESULTS_PANEL.add(createDescriptionLabelBox("This section shows the results of a monitoring session. "));
		RESULTS_PANEL.add(createDescriptionLabelBox("Every time a new program is run in monitoring mode, this section is cleared and reset. "));
		RESULTS_PANEL.add(createVerticalSpacer(15));
		RESULTS_PANEL.add(RESULTS_DATA_PANEL);
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
	
	private JPanel createResultsDataPanel(ResultSet results) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(createDataLineBox("Value", "Min", "Max", "Unit"));
		
		AxisExtremity xset = results.getX();
		AxisExtremity yset = results.getY();
		AxisExtremity zset = results.getZ();
		
		DecimalFormat df = new DecimalFormat("0.00");	// Use double-digit precision
		
		panel.add(createDataLineBox("X", df.format(xset.getMin()), df.format(xset.getMax()), "mm"));
		panel.add(createDataLineBox("Y", df.format(yset.getMin()), df.format(yset.getMax()), "mm"));
		panel.add(createDataLineBox("Z", df.format(zset.getMin()), df.format(zset.getMax()), "mm"));
		
		panel.add(createDataLineBox("Speed", "", df.format(results.getSpeedMax()), "mm/s"));
		
		return panel;
	}
	
	private Box createDataLineBox(String col1, String col2, String col3, String col4) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel label1 = new JLabel(col1);
		JLabel label2 = new JLabel(col2);
		JLabel label3 = new JLabel(col3);
		JLabel label4 = new JLabel(col4);
		
		Dimension columnDimension = new Dimension(120, 30);
		
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setHorizontalAlignment(SwingConstants.LEFT);
		
		label1.setPreferredSize(columnDimension);
		label2.setPreferredSize(columnDimension);
		label3.setPreferredSize(columnDimension);
		label4.setPreferredSize(columnDimension);
		
		label1.setMaximumSize(columnDimension);
		label2.setMaximumSize(columnDimension);
		label3.setMaximumSize(columnDimension);
		label4.setMaximumSize(columnDimension);
		
		box.add(label1);
		box.add(createHorizontalSpacer(15));
		box.add(label2);
		box.add(label3);
		box.add(createHorizontalSpacer(15));
		box.add(label4);
		
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
	
	private Component createHorizontalSpacer(int width) {
		return Box.createRigidArea(new Dimension(width, 0));
	}
	
}
