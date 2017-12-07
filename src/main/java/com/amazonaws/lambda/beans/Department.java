package com.amazonaws.lambda.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Department {
	long ID;
	String Name;
	String NumberOfUsers;
	
	@JsonProperty("ID")
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	
	@JsonProperty("Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@JsonProperty("NumberOfUsers")
	public String getNumberOfUsers() {
		return NumberOfUsers;
	}
	public void setNumberOfUsers(String numberOfUsers) {
		NumberOfUsers = numberOfUsers;
	}

}
