package com.amazonaws.lambda.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseEnrollment {
	private CourseUserBean User;
	private String CourseName;
	private String CourseID;
	private String SessionName;
	private String SessionID;
	private String RequirementType;
	private String DueDate;
	private String StartDate;
	private String CompletionDate;
	private String Score;
	private String CourseStatus;
	private String EnrollmentDate;
	private String TimeSpent;
	private String LicenseExpirationDate;
	
	@JsonProperty("LicenseExpirationDate")
	public String getLicenseExpirationDate() {
		return LicenseExpirationDate;
	}
	public void setLicenseExpirationDate(String licenseExpirationDate) {
		LicenseExpirationDate = licenseExpirationDate;
	}
	
	@JsonProperty("User")
	public CourseUserBean getUser() {
		return User;
	}
	public void setUser(CourseUserBean user) {
		User = user;
	}
	
	@JsonProperty("CourseName")
	public String getCourseName() {
		return CourseName;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	
	@JsonProperty("CourseID")
	public String getCourseID() {
		return CourseID;
	}
	public void setCourseID(String courseID) {
		CourseID = courseID;
	}
	
	@JsonProperty("SessionName")
	public String getSessionName() {
		return SessionName;
	}
	public void setSessionName(String sessionName) {
		SessionName = sessionName;
	}
	
	@JsonProperty("SessionID")
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
	
	@JsonProperty("RequirementType")
	public String getRequirementType() {
		return RequirementType;
	}
	public void setRequirementType(String requirementType) {
		RequirementType = requirementType;
	}
	
	@JsonProperty("DueDate")
	public String getDueDate() {
		return DueDate;
	}
	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}
	
	@JsonProperty("StartDate")
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	
	@JsonProperty("CompletionDate")
	public String getCompletionDate() {
		return CompletionDate;
	}
	public void setCompletionDate(String completionDate) {
		CompletionDate = completionDate;
	}
	
	@JsonProperty("Score")
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	
	@JsonProperty("CourseStatus")
	public String getCourseStatus() {
		return CourseStatus;
	}
	public void setCourseStatus(String courseStatus) {
		CourseStatus = courseStatus;
	}
	
	@JsonProperty("EnrollmentDate")
	public String getEnrollmentDate() {
		return EnrollmentDate;
	}
	public void setEnrollmentDate(String enrollmentDate) {
		EnrollmentDate = enrollmentDate;
	}
	
	@JsonProperty("TimeSpent")
	public String getTimeSpent() {
		return TimeSpent;
	}
	public void setTimeSpent(String timeSpent) {
		TimeSpent = timeSpent;
	}
}
