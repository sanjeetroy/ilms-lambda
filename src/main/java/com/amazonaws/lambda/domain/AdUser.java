package com.amazonaws.lambda.domain;

import java.util.HashMap;

import java.util.Map;

import com.univocity.parsers.annotations.Parsed;

public class AdUser {
	@Parsed(field = "First Name",defaultNullRead="")
	private String firstName;
    @Parsed(field = "Last Name",defaultNullRead="")
	private String lastName;
    @Parsed(field = "Full Name",defaultNullRead="")
    private String FullName;
//	@Parsed(field = "Company email",defaultNullRead="")
	@Parsed(field = "Email - Work",defaultNullRead="")
	private String emailId;
//	@Parsed(field = "Start Date") //
	@Parsed(field = "Hire Date") //
	private String hireDate;
//	@Parsed(field = "Employee Type",defaultNullRead="") //
	@Parsed(field = "Employee Sub-Type",defaultNullRead="") //
	private String employeeType;
//	@Parsed(field = "Departments",defaultNullRead="AppDirect Default Department")
	@Parsed(field = "Cost Center",defaultNullRead="AppDirect Default Department")
	private String department;
//	@Parsed(field = "Job Title",defaultNullRead="")
	@Parsed(field = "Business Title",defaultNullRead="")
	private String jobTitle;
	@Parsed(field = "Division",defaultNullRead="AppDirect Default Division")
	private String division;
//	@Parsed(field = "Reports To Full Name",defaultNullRead="")
	@Parsed(field = "Employee's Manager",defaultNullRead="")
	private String supervisorName;
//	@Parsed(field = "Reports To Email",defaultNullRead="")
	@Parsed(field = "Manager's Email",defaultNullRead="")
	private String supervisorEmail;
//	@Parsed(field = "Office City",defaultNullRead="")
	@Parsed(field = "Location",defaultNullRead="")
	private String city;
	@Parsed(field = "Office Country",defaultNullRead="")
	private String country;
//	@Parsed(field = "Office Location Address 1",defaultNullRead="")
	@Parsed(field = "Addresses",defaultNullRead="")
	private String address1;
	/*@Parsed(field = "Office Location Address 2",defaultNullRead="")
	private String address2;
	@Parsed(field = "Office Location Zip",defaultNullRead="")
	private String zipCode;
	@Parsed(field = "User status",defaultNullRead="")
	private String userStatus;
	@Parsed(field = "Subsidiary Company",defaultNullRead="") 
	private String subsidiaryCompany;
	*/
	public DiffUser equals(LmsUser lmsUser){
		Map<String,String> list =  new HashMap<String,String>();
		Map<String,String> listOld =  new HashMap<String,String>();
		
		DiffUser diffUser = new DiffUser();
		Profile lmsProfile = lmsUser.getProfile();
		
		boolean result = true;
		
		/*if(!getUserStatus().equals(lmsUser.getUserStatus()) && !getUserStatus().contains(lmsUser.getUserStatus())){
			list.put("userStatus",getUserStatus());
			listOld.put("userStatus",lmsUser.getUserStatus());
			result = false;
		}*/
		
		/*if(!getFirstName().equals(lmsProfile.getFirstName())){
			list.put("firstName",getFirstName());
			listOld.put("firstName",lmsProfile.getFirstName());
			result = false;
		}
		if(!getLastName().equals(lmsProfile.getLastName())){
			list.put("lastName",getLastName());
			listOld.put("lastName",lmsProfile.getLastName());
			result = false;
		}
		if(!getEmailId().equals(lmsProfile.getEmailId())){
			list.put("emailId",getEmailId());
			listOld.put("emailId",lmsProfile.getEmailId());
			result = false;
		}
		if(!getDepartment().equals(lmsProfile.getDepartment())){
			list.put("department",getDepartment());
			listOld.put("department",lmsProfile.getDepartment());
			result = false;
		}
		if(!getJobTitle().equals(lmsProfile.getJobTitle())){
			list.put("jobTitle",getJobTitle());
			listOld.put("jobTitle",lmsProfile.getJobTitle());
			result = false;
		}
		if(!getDivision().equals(lmsProfile.getDivision())){
			list.put("division",getDivision());
			listOld.put("division",lmsProfile.getDivision());
			result = false;
		}*/
		if(!getSupervisorName().equals(lmsProfile.getSupervisorName())){
			list.put("supervisorName",getSupervisorName());
			listOld.put("supervisorName",lmsProfile.getSupervisorName());
			result = false;
		}
		if(!getSupervisorEmail().equals(lmsProfile.getSupervisorEmail())){
			list.put("supervisorEmail",getSupervisorEmail());
			listOld.put("supervisorEmail",lmsProfile.getSupervisorEmail());
			result = false;
		}
		/*if(!getCity().equals(lmsProfile.getCity())){
			list.put("city",getCity());
			listOld.put("city",lmsProfile.getCity());
			result = false;
		}
		if(!getCountry().equals(lmsProfile.getCountry())){
			list.put("country",getCountry());
			listOld.put("country",lmsProfile.getCountry());
			result = false;
		}
		if(!getAddress1().equals(lmsProfile.getAddress1())){
			list.put("address1",getAddress1());
			listOld.put("address1",lmsProfile.getAddress1());
			result = false;
		}
		if(!getAddress2().equals(lmsProfile.getAddress2())){
			list.put("address2",getAddress2());
			listOld.put("address2",lmsProfile.getAddress2());
			result = false;
		}*/
		/*if(!getSubsidiaryCompany().equals(lmsProfile.getCompany())){
			list.put("company",getSubsidiaryCompany());
			listOld.put("company",lmsProfile.getCompany());
			result = false;
		}*/
		/*if(!getHireDate().equals(lmsProfile.getHireDate())){
			list.put("hireDate",getHireDate());
			listOld.put("hireDate",lmsProfile.getHireDate());
			result = false;
		}*/
		/*if(!getZipCode().equals(lmsProfile.getZipCode())){
			list.put("zipCode",getZipCode());
			listOld.put("zipCode",lmsProfile.getZipCode());
			result = false;
		}*/
		
		diffUser.setUpdated(result);
		diffUser.setDiffIndex(list);
		diffUser.setDiffIndexOld(listOld);
		/*
		result = this.firstName.equals(user.getFirstName()) && this.lastName.equals(user.getLastName());
		result = result && this.emailId.equals(user.getEmailId()) && this.department.equals(user.getDepartment());
		result = result && this.jobTitle.equals(user.getJobTitle()) && this.division.equals(user.getDivision());
		result = result && this.supervisorName.equals(user.getSupervisorName()) && this.supervisorEmail.equals(user.getSupervisorEmail());
		result = result && this.city.equals(user.getCity()) && this.country.equals(user.getCountry());
		result = result && this.address1.equals(user.getAddress1()) && this.address2.equals(user.getAddress2());
		result = result && this.userStatus.equals(user.getUserStatus());
		*/
		return diffUser;
	}
	
	/*public String getZipCode() {
		if(zipCode != null){
			return zipCode.trim();
		}
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}*/

	public String getFirstName() {
		if(firstName != null){
			return firstName.trim().replaceAll(" +", " ");
		}
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public String getEmailId() {
		if(emailId != null){
			return emailId.trim().replaceAll(" +", " ");
		}
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	public String getEmployeeType() {
		if(employeeType != null){
			return employeeType.trim().replaceAll(" +", " ");
		}
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
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
	public String getJobTitle() {
		if(jobTitle != null){
			return jobTitle.trim().replaceAll(" +", " ");
		}
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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
	public String getAddress1() {
		if(address1 != null){
			return address1.trim().replaceAll(" +", " ");
		}
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/*public String getAddress2() {
		if(address2 != null){
			return address2.trim().replaceAll(" +", " ");
		}
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
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
	public String getSubsidiaryCompany() {
		if(subsidiaryCompany != null){
			return subsidiaryCompany.trim().replaceAll(" +", " ");
		}
		return subsidiaryCompany;
	}
	public void setSubsidiaryCompany(String subsidiaryCompany) {
		this.subsidiaryCompany = subsidiaryCompany;
	}
*/

}
