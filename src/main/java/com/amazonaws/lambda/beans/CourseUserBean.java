package com.amazonaws.lambda.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseUserBean {
	private String ID;
	private String UserStatus;
	private CourseProfileBean Profile_Basic;
	
	@JsonProperty("ID")
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	@JsonProperty("UserStatus")
	public String getUserStatus() {
		return UserStatus;
	}
	public void setUserStatus(String userStatus) {
		UserStatus = userStatus;
	}
	
	@JsonProperty("Profile_Basic")
	public CourseProfileBean getProfile_Basic() {
		return Profile_Basic;
	}
	public void setProfile_Basic(CourseProfileBean profile_Basic) {
		Profile_Basic = profile_Basic;
	}
}
