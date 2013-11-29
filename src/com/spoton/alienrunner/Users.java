package com.spoton.alienrunner;

public class Users {
	
	private String userId;
	private double xCoord;
	private double yCoord;
	
	


	public Users(String userId, double xCoord, double yCoord ){
		this.userId = userId;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		
	}
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public double getxCoord() {
		return xCoord;
	}


	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}


	public double getyCoord() {
		return yCoord;
	}


	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}


}
