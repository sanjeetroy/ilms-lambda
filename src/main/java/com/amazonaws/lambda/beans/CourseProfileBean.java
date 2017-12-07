package com.amazonaws.lambda.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseProfileBean {
	private String F001;
	private String F003;
	private String F004;
	private String F006;
	private String F015;
	private String F019;
	private String F019_ID;
	private String F020;
	private String F020_ID;
	private String F021;
	private String F021_ID;
	
	@JsonProperty("F001")
	public String getF001() {
		return F001;
	}
	public void setF001(String f001) {
		F001 = f001;
	}
	
	public String getFirstName() {
		return getF001();
	}
	
	@JsonProperty("F003")
	public String getF003() {
		return F003;
	}
	public void setF003(String f003) {
		F003 = f003;
	}
	
	public String getLastName() {
		return getF003();
	}
	
	@JsonProperty("F004")
	public String getF004() {
		return F004;
	}
	public void setF004(String f004) {
		F004 = f004;
	}
	
	public String getEmployeeId() {
		return getF004();
	}
	
	@JsonProperty("F006")
	public String getF006() {
		return F006;
	}
	public void setF006(String f006) {
		F006 = f006;
	}
	
	public String getUserLanguage() {
		return getF006();
	}
	
	@JsonProperty("F015")
	public String getF015() {
		return F015;
	}
	public void setF015(String f015) {
		F015 = f015;
	}
	
	public String getEmailId() {
		return getF015();
	}
	
	@JsonProperty("F019")
	public String getF019() {
		return F019;
	}
	public void setF019(String f019) {
		F019 = f019;
	}
	
	public String getRegion() {
		return getF019();
	}
	
	@JsonProperty("F019_ID")
	public String getF019_ID() {
		return F019_ID;
	}
	public void setF019_ID(String f019_ID) {
		F019_ID = f019_ID;
	}
	
	public String getRegionId() {
		return getF019_ID();
	}
	
	@JsonProperty("F020")
	public String getF020() {
		return F020;
	}
	public void setF020(String f020) {
		F020 = f020;
	}
	
	public String getDivision() {
		return getF020();
	}
	
	@JsonProperty("F020_ID")
	public String getF020_ID() {
		return F020_ID;
	}
	public void setF020_ID(String f020_ID) {
		F020_ID = f020_ID;
	}
	
	public String getDivisionId() {
		return getF020_ID();
	}
	
	@JsonProperty("F021")
	public String getF021() {
		return F021;
	}
	public void setF021(String f021) {
		F021 = f021;
	}
	
	public String getDepartment() {
		return getF021();
	}
	
	@JsonProperty("F021_ID")
	public String getF021_ID() {
		return F021_ID;
	}
	public void setF021_ID(String f021_ID) {
		F021_ID = f021_ID;
	}
	
	public String getDepartmentId() {
		return getF021_ID();
	}
}
