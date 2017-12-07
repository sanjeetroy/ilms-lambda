package com.amazonaws.lambda.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Course {
	
	private String ID;
	private String Name;
	private String Type;
	private String DeliveryType;
	private String DefaultRequirementType;
	private String Credits;
	private String Hours;
	private String Status;
	private DefaultDueDateSettings DefaultDueDateSettings;
	
	@JsonProperty("ID")
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	@JsonProperty("Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@JsonProperty("Type")
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	
	@JsonProperty("DeliveryType")
	public String getDeliveryType() {
		return DeliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		DeliveryType = deliveryType;
	}
	
	@JsonProperty("DefaultRequirementType")
	public String getDefaultRequirementType() {
		return DefaultRequirementType;
	}
	public void setDefaultRequirementType(String defaultRequirementType) {
		DefaultRequirementType = defaultRequirementType;
	}
	
	@JsonProperty("Credits")
	public String getCredits() {
		return Credits;
	}
	public void setCredits(String credits) {
		Credits = credits;
	}
	
	@JsonProperty("Hours")
	public String getHours() {
		return Hours;
	}
	public void setHours(String hours) {
		Hours = hours;
	}
	
	@JsonProperty("Status")
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	@JsonProperty("DefaultDueDateSettings")
	public DefaultDueDateSettings getDefaultDueDateSettings() {
		return DefaultDueDateSettings;
	}
	public void setDefaultDueDateSettings(DefaultDueDateSettings defaultDueDateSettings) {
		this.DefaultDueDateSettings = defaultDueDateSettings;
	}
	
	
	

}
