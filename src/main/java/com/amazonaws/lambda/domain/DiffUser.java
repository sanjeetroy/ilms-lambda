package com.amazonaws.lambda.domain;

import java.util.Map;

public class DiffUser {
	boolean updated;
	long id;
	String emailId;
	Map<String,String> diffIndex;  //holds key-value pair of records from CSV
	Map<String,String> diffIndexOld;  //holds key-value pair of records from ILMS
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmailId() {
		if(emailId != null){
			return emailId.trim().replaceAll(" +", " ");
		}
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Map<String, String> getDiffIndex() {
		return diffIndex;
	}
	public void setDiffIndex(Map<String, String> diffIndex) {
		this.diffIndex = diffIndex;
	}
	public Map<String, String> getDiffIndexOld() {
		return diffIndexOld;
	}
	public void setDiffIndexOld(Map<String, String> diffIndexOld) {
		this.diffIndexOld = diffIndexOld;
	}
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	public boolean isUpdated() {
		return updated;
	}
	

}
