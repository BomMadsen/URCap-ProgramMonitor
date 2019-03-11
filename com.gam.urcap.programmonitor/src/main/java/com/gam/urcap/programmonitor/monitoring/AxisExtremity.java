package com.gam.urcap.programmonitor.monitoring;

public class AxisExtremity {

	private Double MIN;
	private Double MAX;
	
	public AxisExtremity() {
		
	}
	
	public AxisExtremity(Double min, Double max) {
		MIN = min;
		MAX = max;
	}
	
	public Double getMin() {
		return MIN;
	}
	public Double getMax() {
		return MAX;
	}
	
	public void setMin(double value) {
		MIN = value;
	}
	public void setMax(double value) {
		MAX = value;
	}
	
	public boolean isResolved() {
		return (MIN!=null) && (MAX!=null);
	}
	
}
