package com.amazonaws.lambda.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBean
{
    
    private long ID;
    private ProfileBean Profile;
    private String UserStatus;
    
    @JsonProperty("ID")
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	
	@JsonProperty("Profile")
	public ProfileBean getProfile() {
		return Profile;
	}
	public void setProfile(ProfileBean profile) {
		Profile = profile;
	}
	
	@JsonProperty("UserStatus")
	public String getUserStatus() {
		return UserStatus;
	}
	public void setUserStatus(String userStatus) {
		UserStatus = userStatus;
	}
    
    
    
}

