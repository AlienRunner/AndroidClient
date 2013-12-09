package com.spoton.alienrunner;

//import java.io.Serializable;

public class User {//implements Serializable{
	
	private String userId;
	private double xCoord;
	private double yCoord;
	private String race;
	
	public User(String userId, double xCoord, double yCoord, String race ){
		this.userId = userId;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.race = race;
		
		
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
	
	public String getRace(){
		return race;
	}
	public void setRace(String race){
		this.race = race;
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
	
	public String encrypt(){
		String userString = "[" + userId + "," + xCoord
		+ "," + yCoord + "," + race + "]";
		return userString;
	}

}
