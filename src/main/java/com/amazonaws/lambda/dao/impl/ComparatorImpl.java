package com.amazonaws.lambda.dao.impl;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.amazonaws.lambda.beans.Department;
import com.amazonaws.lambda.beans.Division;
import com.amazonaws.lambda.beans.Region;
import com.amazonaws.lambda.common.UpdatedJson;
import com.amazonaws.lambda.dao.Comparator;
import com.amazonaws.lambda.domain.AdUser;
import com.amazonaws.lambda.domain.DiffUser;
import com.amazonaws.lambda.domain.LmsUser;
import com.amazonaws.lambda.domain.NewUser;
import com.amazonaws.lambda.domain.UpdatedUsers;
import com.amazonaws.lambda.ilmswrapper.LambdaFunctionHandler;
import com.amazonaws.lambda.service.impl.LmsApiCallerServiceImpl;
import com.amazonaws.lambda.util.IlmsFieldToNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ComparatorImpl implements Comparator {
	
	private ArrayList<DiffUser> diffUsers = new ArrayList<DiffUser>();
	private ArrayList<NewUser> newUsers = new ArrayList<NewUser>();
	private UpdatedUsers updatedUsers = new UpdatedUsers();
	
	private List<String> activeUsers = new ArrayList<String>();
	private Map<String,Long> lmsToBeInActivedUsers = new TreeMap<String,Long>(String.CASE_INSENSITIVE_ORDER);
	
	private Map<String,LmsUser> lmsUsers ;
	private Map<String,Region> lmsRegions ;
	private Map<String,Division> lmsDivisions ;
	private Map<String,Department> lmsDepartments ;
	
	private int onlyCompare = 1;
	
	static final Logger log = LogManager.getLogger(ComparatorImpl.class);
	
	private int diffUsersCount = 0;
	private int notExistsCount = 0;
	private int updatedCount = 0;
	private int total = 0;
	private int totalAppdirectUser = 0;
	
	public void logSummary(){
		
		log.info("Total number of USERS in CSV = " + total);
		log.info("Total number of USERS in CSV with Appdirect Account = " + totalAppdirectUser);
		log.info("Total number of USERS in ILMS = " + lmsUsers.size());
		log.info("Number of Users that Does NOT EXIST in ILMS = " + notExistsCount);
		log.info("Number of Users that are NOT UPDATED = " + diffUsersCount);
		
		log.info("Number of Users that are UP-To-DATE = " + updatedCount);
		logUpdatedUsersEmail();
		
		log.info("Number of Users that will be UPDATED if noOps = flase => " + diffUsersCount);
		logDiffUsersEmail();
		
		log.info("Number of Users that will be ADDED to ILMS if noOps = flase => " + notExistsCount);
		logNewUsersEmail();
		
		int extraUsers = lmsUsers.size() - activeUsers.size();
		log.info("Number of Users that are in ILMS but NOT in Csv = " + extraUsers);
		
		log.info("Number of Users that will be InActivated in ILMS if noOps = flase => " + extraUsers);
		logExtraUsersEmail();
		
	}
	
public void logSummaryOps(){
		
		log.info("Total number of USERS in CSV = " + total);
		log.info("Total number of USERS in CSV with Appdirect Account = " + totalAppdirectUser);
		log.info("Total number of USERS in ILMS = " + lmsUsers.size());
		
		log.info("Number of Users that are UP-To-DATE = " + updatedCount);
		logUpdatedUsersEmail();
		
		log.info("Number of Users that has been UPDATED  => " + diffUsersCount);
		logDiffUsersEmail();
		
		log.info("Number of Users that has been ADDED to ILMS  => " + notExistsCount);
		logNewUsersEmail();
		
		int extraUsers = lmsUsers.size() - activeUsers.size();
		log.info("Number of Users that are in ILMS but NOT in Csv = " + extraUsers);
		
		log.info("Number of Users that has been InActivated in ILMS  => " + extraUsers);
		logExtraUsersEmail();
		
	}
	
	public void logExtraUsersEmail(){
		Set<String> lmsUsersEmail = lmsUsers.keySet();
		Set<String> lmsActiveUsersEmail_caseInSensitive = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		
		lmsActiveUsersEmail_caseInSensitive.addAll(lmsUsersEmail);
		
		Set<String> activeUsers_caseInSensitive = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		activeUsers_caseInSensitive.addAll(activeUsers);
		
		lmsActiveUsersEmail_caseInSensitive.removeAll(activeUsers_caseInSensitive);
		log.info("CSV Emails = " + Arrays.toString(activeUsers_caseInSensitive.toArray()));
		log.info("Emails of the Users that will be InActivated = " + Arrays.toString(lmsActiveUsersEmail_caseInSensitive.toArray()));
		
		for(String email:lmsActiveUsersEmail_caseInSensitive){
			lmsToBeInActivedUsers.put(email, lmsUsers.get(email).getId());
		}
		//lmsToBeInActivedUsers.put("test.test@test.com", 3229528L);
	}
	
	public void logUpdatedUsersEmail(){
		if(updatedCount > 0){
			log.info("Emails of the Users that are UP-TO-DATE = " + Arrays.toString(updatedUsers.getUsers().toArray()));
		}
	}
	
	public void logDiffUsersEmail(){
		String diffStr = "[";
		int diffSize = diffUsers.size();
		if(diffSize > 0){
			diffStr = diffStr + diffUsers.get(0).getEmailId();
		}
		for(int i=1;i<diffSize;i++){
			diffStr = diffStr + "," +  diffUsers.get(i).getEmailId();
		}
		diffStr = diffStr + "]";
		log.info("Emails of the Users that will be UPDATED = " + diffStr);
		
	}
	
	public void logNewUsersEmail(){
		String newStr = "[";
		int newSize = newUsers.size();
		if(newSize > 0){
			newStr = newStr + newUsers.get(0).getEmailId();
		}
		for(int i=1;i<newSize;i++){
			newStr = newStr + "," +  newUsers.get(i).getEmailId();
		}
		newStr = newStr + "]";
		
		log.info("Emails of the Users that will be ADDED = " + newStr);
	}
	
	public void compare(ArrayList<AdUser> adUsers){
		onlyCompare = 1;
		setLmsUsers();
		setLmsRegions();
		compareAndSet(adUsers);
		logDiffUsers();
		logSummary();
	}
	
	public void compareAndUpdate(ArrayList<AdUser> adUsers){
		onlyCompare = 0;
		setLmsUsers();
		setLmsRegions();
		compareAndSet(adUsers);
		logDiffUsers();
		logSummary();
		
		updateLms();
		addToLms();
		inActivateInLms(lmsToBeInActivedUsers);
		
		logSummaryOps();
	}
	
	public void inActivateInLms(Map<String,Long> lmsToBeInActivedUsers){
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		lmsApiCallerServiceImpl.inActivateUsers(lmsToBeInActivedUsers);
	}
	
	public void setLmsUsers(){
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		log.info("Calling LmsApiCaller to get All Users");
		lmsUsers = lmsApiCallerServiceImpl.getAllUsers();
	}
	
	public void setLmsRegions(){
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		log.info("Calling LmsApiCaller to get All Regions");
		lmsRegions = lmsApiCallerServiceImpl.getAllRegions();
	}
	
	public void setLmsDivisions(long regionId){
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		log.info("Calling LmsApiCaller to get All Divisions");
		lmsDivisions = lmsApiCallerServiceImpl.getAllDivisions(regionId);
	}
	
	public void setLmsDepartments(long regionId,long divisionId){
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		log.info("Calling LmsApiCaller to get All Departments");
		lmsDepartments = lmsApiCallerServiceImpl.getAllDepartments(regionId,divisionId);
	}
	
	public boolean regionExist(String region){
		if(lmsRegions.get(region)==null){
			return false;
		}
		return true;
	}
	
	public int addRegion(String region){
		String payload = "{\"name\":\"" + region + "\"}";
		int successCode = -1;
		
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		log.info("Calling LmsApiCaller ");
		log.info("Trying to add region = " + region);
		successCode = lmsApiCallerServiceImpl.addRegion(region,payload);
		return successCode;
	}
	
	public int addDivision(String region,String division){
		long regionId = lmsRegions.get(region).getID();
		
		String payload = "{\"name\":\"" + division + "\"}";
		int successCode = -1;
		
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		log.info("Calling LmsApiCaller..");
		
		log.info("Trying to add division = " + division + " in region = " + region);
		successCode = lmsApiCallerServiceImpl.addDivision(regionId,division,payload);
		return successCode;
	}
	
	public int addDepartment(String region,String division,String department){
		
		long regionId = lmsRegions.get(region).getID();
		//setLmsDivisions(regionId);
		
		long divisionId = lmsDivisions.get(division).getID();
		
		String payload = "{\"name\":\"" + department + "\"}";
		int successCode = -1;
		
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		log.info("Calling LmsApiCaller..");
		
		log.info("Trying to add department " + department + " in division = " + division + " in region = " + region);
		successCode = lmsApiCallerServiceImpl.addDepartment(regionId,divisionId,department,payload);
		return successCode;
	}
	
	public boolean divisionExist(String region,String division){
		if(lmsRegions.get(region) == null){
			setLmsRegions();
		}
		setLmsDivisions(lmsRegions.get(region).getID());
		
		log.info("Updated lmsDivision = " + Arrays.toString(lmsDivisions.keySet().toArray()));
		if(lmsDivisions.get(division)==null){
			return false;
		}
		return true;
	}
	
	public boolean departmentExist(String region,String division,String department){
		setLmsDivisions(lmsRegions.get(region).getID());		
		setLmsDepartments(lmsRegions.get(region).getID(),lmsDivisions.get(division).getID());
		
		if(lmsDepartments.get(department)==null){
			return false;
		}
		return true;
	}
	
	public void addToNewUsers(AdUser adUser){
		Map<String,String> profileFields = new HashMap<String,String>();
		NewUser newUser = new NewUser();
		
		newUser.setEmailId(adUser.getEmailId());
		profileFields.put("firstName", adUser.getFirstName());
		profileFields.put("lastName", adUser.getLastName());
		profileFields.put("emailId",adUser.getEmailId());
		
		profileFields.put("jobTitle",adUser.getJobTitle());
		
		profileFields.put("supervisorName",adUser.getSupervisorName());
		profileFields.put("supervisorEmail",adUser.getSupervisorEmail());
		profileFields.put("city",adUser.getCity());
		profileFields.put("country",adUser.getCountry());
		profileFields.put("address1",adUser.getAddress1());
		profileFields.put("address2",adUser.getAddress2());
		profileFields.put("company",adUser.getSubsidiaryCompany());
		
		if(onlyCompare == 0){
			if(regionExist(adUser.getCity())){
				profileFields.put("region", adUser.getCity());
			}else{
				log.debug("Region \"" + adUser.getCity() + "\" of user " + adUser.getEmailId() + " Does NOT EXIST in ILMS");
				log.info("Adding region = " + adUser.getCity());
				int responseCode = addRegion(adUser.getCity());
				if(responseCode == 201){
					profileFields.put("region", adUser.getCity());
				}
			}
			
			if(divisionExist(adUser.getCity(),adUser.getDivision())){
				profileFields.put("division",adUser.getDivision());
			}else{
				log.debug("Division \"" + adUser.getDivision() + "\" Does NOT EXIST in ILMS In region = \""  + adUser.getCity() + "\" of user " + adUser.getEmailId());
				log.info("Adding Division = " + adUser.getDivision());
				int responseCode = addDivision(adUser.getCity(),adUser.getDivision());
				if( responseCode == 201){
					profileFields.put("division",adUser.getDivision());
				}
			}
			
			if(departmentExist(adUser.getCity(),adUser.getDivision(),adUser.getDepartment())){
				profileFields.put("department",adUser.getDepartment());
			}else{
				log.debug("Department \"" + adUser.getDepartment() + "\" Does NOT EXIST in ILMS In Division = \"" + adUser.getDivision() + "\" Of region = \""  + adUser.getCity() + "\" of user " + adUser.getEmailId());
				log.info("Adding Department = " + adUser.getDepartment());
				int responseCode = addDepartment(adUser.getCity(),adUser.getDivision(),adUser.getDepartment()); 
				if(responseCode == 201){
					profileFields.put("department",adUser.getDepartment());
				}
			}
		}
		
		
		/*profileFields.put("hireDate",adUser.getHireDate());
		profileFields.put("zipCode",adUser.getZipCode());
		*/ 
		
		newUser.setProfileFields(profileFields);
		newUsers.add(newUser);
	}
	
	public void compareAndSetNewUser(AdUser adUser){
		
		String email = adUser.getEmailId();
		LmsUser lmsUser = lmsUsers.get(email);
			
		if(lmsUser == null){
			log.info("User with EmailId:" + email + " does NOT EXISTS");
			notExistsCount++;
			addToNewUsers(adUser);
		}
	}
	
	public void compareAndSet(ArrayList<AdUser> adUsers){
		int size = adUsers.size();
		total = size;
		for(int i=0;i<size;i++){
			AdUser adUser = adUsers.get(i);
			
			if(adUser.getEmailId().endsWith("@appdirect.com")){
				totalAppdirectUser += 1;
				compareAndSetDiffUser(adUser);
				compareAndSetNewUser(adUser);
			}
			
		}
	}
	
	public void compareAndSetDiffUser(AdUser adUser){
		
		String email = adUser.getEmailId();
		LmsUser lmsUser = lmsUsers.get(email);
			
		long userID = -1;
		String emailId = null;
			
		if(lmsUser != null){
			userID = lmsUser.getId();
			emailId = lmsUser.getProfile().getEmailId();
			DiffUser d = adUser.equals(lmsUser);
			if(d.isUpdated()){
				log.info("User with EmailId: "+ email + " is Updated. NO DIFFERENCE FOUND");
				activeUsers.add(email);
				updatedUsers.add(email);
				updatedCount++;
			}
			else{
				activeUsers.add(emailId);
				d.setId(userID);
				d.setEmailId(emailId);
				diffUsers.add(d);
				diffUsersCount++;
			}
		}
		
	}
	
	public void logDiffUsers(){
		int size = diffUsers.size();
		if(size>0){
			log.info("Following Differences Are FOUND..");
			for(int i=0;i<size;i++){
				DiffUser diffUser = diffUsers.get(i);
				log.info("EmailId:" + diffUser.getEmailId());
				log.debug("Id:" + diffUser.getId());
				log.info("Difference Index = " + diffUser.getDiffIndex().keySet().toString());
				
				log.info("If \"noOps = false\" Following Update Will take place : ");
				logValues(diffUser);
			}
			
		}		
	}
	
	public void logValues(DiffUser diffUser){
		Map<String,String> diffIndex = diffUser.getDiffIndex();
		Map<String,String> diffIndexOld = diffUser.getDiffIndexOld();
		Set<String> keys = diffIndex.keySet();
		
		List<UpdatedJson> updateObj = new ArrayList<UpdatedJson>();
		ObjectMapper mapper = new ObjectMapper();
		
		for(String index:keys){
			UpdatedJson updatedJson = new UpdatedJson();
			updatedJson.setKey(index);
			updatedJson.setValue("From:-> " + diffIndexOld.get(index) + " To:-> " + diffIndex.get(index));
			updateObj.add(updatedJson);
		}
		String diffFieldsValue = "";
		try{
			diffFieldsValue = mapper.writeValueAsString(updateObj);
		}catch(JsonProcessingException ex){
			log.error("Exception : " + ex);
		}
		
		String formattedDiffFieldsValue = "{ \"updates\" : " + diffFieldsValue + " }";
		log.info(formattedDiffFieldsValue);
	}
	
	public void updateLms(){
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		lmsApiCallerServiceImpl.updateUsers(diffUsers);
	}
	
	public void addToLms(){
		LmsApiCallerServiceImpl lmsApiCallerServiceImpl = new LmsApiCallerServiceImpl();
		lmsApiCallerServiceImpl.addUsers(newUsers);
	}
	
	public String getCurrentLocalDateTimeStamp() {
	    return LocalDateTime.now()
	       .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
	
}
