package com.amazonaws.lambda.domain;

import com.univocity.parsers.annotations.Parsed;

public class LmsUser {
	private long id;
	@Parsed(field = "User status",defaultNullRead="")
	private String userStatus;
	Profile profile;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserStatus() {
		if(userStatus != null){
			return userStatus.trim().replaceAll(" +", " ");
		}
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
