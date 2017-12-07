package com.amazonaws.lambda.domain;

import java.util.Map;

public class NewUser {
	private String emailId;
	private Map<String,String> profileFields;
	
	public String getEmailId() {
		if(emailId != null){
			return emailId.trim().replaceAll(" +", " ");
		}
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Map<String, String> getProfileFields() {
		return profileFields;
	}
	public void setProfileFields(Map<String, String> profileFields) {
		this.profileFields = profileFields;
	}
	
	

}
