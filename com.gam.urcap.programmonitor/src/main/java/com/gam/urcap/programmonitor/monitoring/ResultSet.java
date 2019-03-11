package com.gam.urcap.programmonitor.monitoring;

public class ResultSet {

	private AxisExtremity X_AXIS = new AxisExtremity();
	private AxisExtremity Y_AXIS = new AxisExtremity();
	private AxisExtremity Z_AXIS = new AxisExtremity();
	private Double SPEED_MAX;
	
	public void setX(Double min, Double max) {
		X_AXIS.setMin(min);
		X_AXIS.setMax(max);
	}
	
	public void setY(Double min, Double max) {
		Y_AXIS.setMin(min);
		Y_AXIS.setMax(max);
	}
	
	public void setZ(Double min, Double max) {
		Z_AXIS.setMin(min);
		Z_AXIS.setMax(max);
	}
	
	public void setSpeedMax(Double speed) {
		SPEED_MAX = speed;
	}
	
	public AxisExtremity getX() {
		return X_AXIS;
	}
	
	public AxisExtremity getY() {
		return Y_AXIS;
	}
	
	public AxisExtremity getZ() {
		return Z_AXIS;
	}
	
	public Double getSpeedMax() {
		return SPEED_MAX;
	}
	
	public boolean isResolved() {
		return X_AXIS.isResolved() && Y_AXIS.isResolved() && Z_AXIS.isResolved() && SPEED_MAX != null;
	}
	
}
