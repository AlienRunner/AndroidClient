package com.spoton.alienrunner;

public class User {
	
	private String userId;
	private double xCoord;
	private double yCoord;
	
	public User(String userId, double xCoord, double yCoord ){
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

	public boolean equals(User b) {
		if(b.getUserId().equals(userId)){
			return true;
		}else{ 
			return false;
		}
	}

	@Override public boolean equals(Object o) {
	    if (!(o instanceof User)) return false;
	    return equals((User)o);
	}

}
