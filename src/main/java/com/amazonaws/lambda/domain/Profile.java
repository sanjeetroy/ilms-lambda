package com.amazonaws.lambda.domain;

import com.univocity.parsers.annotations.Parsed;

public class Profile {

	@Parsed(field = "First Name",defaultNullRead="")
	private String firstName;
	private String middleName;
	private String lastName;
	private String employeeId;
	private String hireDate;
	
	private String userLanguage;
	private String jobTitle;
	private String address1;
	private String address2;
	private String city;
	
	private String country;
	private String state;
	private String province;  //District
	private String zipCode;
	private String emailId;
	
	private String password;
	private String phoneNo;
	private String faxNo;
	private String region;
	private String regionId;
	
	private String division;
	private String divisionId;
	private String department;
	private String departmentId;
	private String company;
	
	private String timeZone;
	private String customField1;
	private String customField2;
	private String customField3;
	private String customField4;
	
	private String registrationType;
	private String registrationDate;
	private String supervisorName;
	private String supervisorEmail;
	
	public String getFirstName() {
		if(firstName != null){
			return firstName.trim().replaceAll(" +", " ");			
		}
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		if(middleName != null){
			return middleName.trim().replaceAll(" +", " ");
		}
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		if(lastName != null){
			return lastName.trim().replaceAll(" +", " ");			
		}
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmployeeId() {
		if(employeeId != null){
			return employeeId.trim().replaceAll(" +", " ");
		}
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getHireDate() {
		if(hireDate != null){
			return hireDate.trim().replaceAll(" +", " ");
		}
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public String getUserLanguage() {
		if(userLanguage != null){
			return userLanguage.trim().replaceAll(" +", " ");
		}
		return userLanguage;
	}
	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}
	public String getJobTitle() {
		if(jobTitle!=null){
			return jobTitle.trim().replaceAll(" +", " ");
		}
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getAddress1() {
		if(address1!=null){
			return address1.trim().replaceAll(" +", " ");
		}
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		if(address2!= null){
			return address2.trim().replaceAll(" +", " ");
		}
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		if(city != null){
			return city.trim().replaceAll(" +", " ");
		}
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		if(country != null){
			return country.trim().replaceAll(" +", " ");
		}
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		if(state != null){
			return state.trim().replaceAll(" +", " ");
		}
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProvince() {
		if(province != null){
			return province.trim().replaceAll(" +", " ");
		}
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getZipCode() {
		if(zipCode != null){
			return zipCode.trim().replaceAll(" +", " ");
		}
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		if(phoneNo != null){
			return phoneNo.trim().replaceAll(" +", " ");
		}
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getFaxNo() {
		if(faxNo != null){
			return faxNo.trim().replaceAll(" +", " ");
		}
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getRegion() {
		if(region != null){
			return region.trim().replaceAll(" +", " ");
		}
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegionId() {
		if(regionId != null){
			return regionId.trim().replaceAll(" +", " ");
		}
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getDivision() {
		if(division != null){
			return division.trim().replaceAll(" +", " ");
		}
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDivisionId() {
		if(divisionId != null){
			return divisionId.trim().replaceAll(" +", " ");
		}
		return divisionId;
	}
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}
	public String getDepartment() {
		if(department != null){
			return department.trim().replaceAll(" +", " ");
		}
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartmentId() {
		if(departmentId != null){
			return departmentId.trim().replaceAll(" +", " ");
		}
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getCompany() {
		if(company != null){
			return company.trim().replaceAll(" +", " ");
		}
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTimeZone() {
		if(timeZone != null){
			return timeZone.trim().replaceAll(" +", " ");
		}
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getCustomField1() {
		if(customField1 != null){
			return customField1.trim().replaceAll(" +", " ");
		}
		return customField1;
	}
	public void setCustomField1(String customField1) {
		this.customField1 = customField1;
	}
	public String getCustomField2() {
		if(customField2 != null){
			return customField2.trim().replaceAll(" +", " ");
		}
		return customField2;
	}
	public void setCustomField2(String customField2) {
		this.customField2 = customField2;
	}
	public String getCustomField3() {
		if(customField3 != null){
			return customField3.trim().replaceAll(" +", " ");
		}
		return customField3;
	}
	public void setCustomField3(String customField3) {
		this.customField3 = customField3;
	}
	public String getCustomField4() {
		if(customField4 != null){
			return customField4.trim().replaceAll(" +", " ");
		}
		return customField4;
	}
	public void setCustomField4(String customField4) {
		this.customField4 = customField4;
	}
	public String getRegistrationType() {
		if(registrationType != null){
			return registrationType.trim().replaceAll(" +", " ");
		}
		return registrationType;
	}
	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}
	public String getRegistrationDate() {
		if(registrationDate != null){
			return registrationDate.trim().replaceAll(" +", " ");
		}
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getSupervisorName() {
		if(supervisorName != null){
			return supervisorName.trim().replaceAll(" +", " ");
		}
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getSupervisorEmail() {
		if(supervisorEmail != null){
			return supervisorEmail.trim().replaceAll(" +", " ");
		}
		return supervisorEmail;
	}
	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}
	
}
