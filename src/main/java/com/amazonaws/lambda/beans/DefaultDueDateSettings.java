package com.amazonaws.lambda.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultDueDateSettings {
	private String DefaultDueDate;
	private String DaysAfterEnrollment;
	
	@JsonProperty("DefaultDueDate")
	public String getDefaultDueDate() {
		return DefaultDueDate;
	}
	public void setDefaultDueDate(String defaultDueDate) {
		DefaultDueDate = defaultDueDate;
	}
	
	@JsonProperty("DaysAfterEnrollment")
	public String getDaysAfterEnrollment() {
		return DaysAfterEnrollment;
	}
	public void setDaysAfterEnrollment(String daysAfterEnrollment) {
		DaysAfterEnrollment = daysAfterEnrollment;
	}
	
	
}
