package com.amazonaws.lambda.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.amazonaws.lambda.beans.Department;
import com.amazonaws.lambda.beans.Division;
import com.amazonaws.lambda.beans.Region;
import com.amazonaws.lambda.common.UpdatedJson;
import com.amazonaws.lambda.dao.impl.LmsApiCallerImpl;
import com.amazonaws.lambda.domain.DiffUser;
import com.amazonaws.lambda.domain.LmsUser;
import com.amazonaws.lambda.domain.NewUser;
import com.amazonaws.lambda.service.LmsApiCallerService;
import com.amazonaws.lambda.util.IlmsFieldToNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LmsApiCallerServiceImpl implements LmsApiCallerService{
	static final Logger log = LogManager.getLogger(LmsApiCallerServiceImpl.class);
	
	public Map<String,LmsUser> getAllUsers(){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		return lmsApiCallerImpl.getAllUsers();
		
	}
	
	public Map<String,Region> getAllRegions(){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		return lmsApiCallerImpl.getAllRegions();
		
	}
	public Map<String,Division> getAllDivisions(Long regionId){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		return lmsApiCallerImpl.getAllDivisions(regionId);
		
	}
	public Map<String,Department> getAllDepartments(long regionId,Long divisionId){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		return lmsApiCallerImpl.getAllDepartments(regionId,divisionId);
		
	}
	
	public void inActivateUsers(Map<String,Long> lmsToBeInActivedUsers){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		Set<String> emails = lmsToBeInActivedUsers.keySet();
		ArrayList<String> failEmail = new ArrayList<String>();
		
		int total = emails.size();
		int count = 0;
		int fail = 0;
		
		int response = 0;
		for(String email:emails){
			log.info("InActivating " + email);
			response = lmsApiCallerImpl.inActivateUsers(lmsToBeInActivedUsers.get(email));
			if(response != 204){
				fail += 1;
				failEmail.add(email);
				log.info("Failed to InActivate : " + email);
			}else{
				count += 1;
				log.info("Successfully InActivated : " + email);
			}
		}
		
		log.info("Total Number of Users to be InActivated = " + total);
		log.info("Number of Users that are Successfully InActivated = " + count);
		log.info("Number of Users whose InActivation Failed = " + fail);
		
		if(fail > 0){
			log.info("Emails of Failed Users = " + Arrays.toString(failEmail.toArray()));
		}
		
	}
	
	public LmsUser getUserById(long userId){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		return lmsApiCallerImpl.getUserById(userId);
	}
	
	public LmsUser getUserByEmailId(String email){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		return lmsApiCallerImpl.getUserByEmailId(email);
	}
	
	public long getUserIdByEmailId(String email){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		return lmsApiCallerImpl.getUserIdByEmailId(email);
	}
	
	public int updateUserProfileById(long userId,String payload){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		
		int successCode = lmsApiCallerImpl.updateUserProfileById(userId, payload);
		return successCode;
		
	}
	
	public int addUser(String emailId,String payload){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		
		int successCode = -1;
		try{
			successCode = lmsApiCallerImpl.addUser(emailId, payload);
		}catch(RuntimeException e){
			log.error(e);
		}
		return successCode;		
	}
	
	public int addRegion(String region,String payload){
		log.info("Adding Region : " + region);
		
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		
		int successCode = lmsApiCallerImpl.addRegion(region, payload);
		return successCode;		
	}
	public int addDivision(long regionId,String division,String payload){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		
		int successCode = lmsApiCallerImpl.addDivision(regionId,division, payload);
		return successCode;		
	}
	
	public int addDepartment(long regionId,long divisionId,String department,String payload){
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		
		int successCode = lmsApiCallerImpl.addDepartment(regionId,divisionId,department, payload);
		return successCode;		
	}
	
	public int updateUserProfileByEmailId(String email,String payload){
		long userId = getUserIdByEmailId(email);
		
		return updateUserProfileById(userId,payload);
	}
	
	public void updateUsers(ArrayList<DiffUser> diffUsers){
		int response = -1;
		int total = diffUsers.size();
		int count = 0;
		int fail = 0;
		ArrayList<String> failEmail = new ArrayList<String>();
		
		for(DiffUser diffUser: diffUsers){
			response = updateUser(diffUser);
			
			if(response == 204){
				count += 1;
			}else{
				fail += 1;
				failEmail.add(diffUser.getEmailId());
			}
		}
		log.info("Total Number of Users to be Updated = " + total);
		log.info("Number of Users that are Successfully Updated = " + count);
		log.info("Number of Users whose Updation Failed = " + fail);
		
		if(fail > 0){
			log.info("Emails of Failed Users = " + Arrays.toString(failEmail.toArray()));
		}
	}
	
	public int updateUser(DiffUser diffUser){
		
		ObjectMapper mapper = new ObjectMapper();
		String lmsField= "";
		
			
		long id = diffUser.getId();
		String email = diffUser.getEmailId();
		Map<String,String> diffIndex = diffUser.getDiffIndex();
		Set<String> keys = diffIndex.keySet();
		
		List<UpdatedJson> updateObj = new ArrayList<UpdatedJson>();
			
		int num;
		int responseCode = -1;	
		for(String index:keys){
			UpdatedJson updatedJson = new UpdatedJson();
			num = IlmsFieldToNumber.get(index);
			if(num < 10){
				lmsField = "F00";
			}
			else{
				lmsField = "F0";
			}
			lmsField += num;
				
			updatedJson.setKey(lmsField);
			updatedJson.setValue(diffIndex.get(index));
			updateObj.add(updatedJson);
		}
			
		try{
			lmsField = mapper.writeValueAsString(updateObj);
		}catch(JsonProcessingException ex){
			log.error("Exception : " + ex);
		}
			
		log.info("Updating Profile of " + email);
		try{
			responseCode = updateUserProfileById(id,lmsField);
		}catch(RuntimeException e){
			log.error(e);
		}
		if(responseCode==204){
			log.info("Update Successful");
		}else{
			log.error("Oh No Something Wrong!!");
			log.error("Update Failed");
		}
		
		return responseCode;
	}

	public void addUsers(ArrayList<NewUser> newUsers){
		int total = newUsers.size();
		int count = 0;
		int fail = 0;
		int response = -1;
		
		ArrayList<String> failEmail = new ArrayList<String>();
		
		for(NewUser newUser: newUsers){
			response = addUser(newUser);
			if(response == 201){
				count += response;
			}else{
				fail += 1;
				failEmail.add(newUser.getEmailId());
			}
			
		}
		
		log.info("Total Number of Users to be Added = " + total);
		log.info("Number of Users that are Successfully Added = " + count);
		log.info("Number of Users whose Addition Failed = " + fail);
		
		if(fail > 0){
			log.info("Emails of Failed Users = " + Arrays.toString(failEmail.toArray()));
		}
		
	}
	
	public int addUser(NewUser newUser){
		
		ObjectMapper mapper = new ObjectMapper();
		long id;
		Map<String,String> profileFields = newUser.getProfileFields();
		Set<String> keys = profileFields.keySet();
		String lmsField= "";
		String addPayload = "";
		String email = newUser.getEmailId();
		
		List<UpdatedJson> updateObj = new ArrayList<UpdatedJson>();
		
		int num;
		int responseCode = -1;
		
		for(String index:keys){
			UpdatedJson updatedJson = new UpdatedJson();
			num = IlmsFieldToNumber.get(index);
			if(num < 10){
				lmsField = "F00";
			}
			else{
				lmsField = "F0";
			}
			lmsField += num;
			
			updatedJson.setKey(lmsField);
			updatedJson.setValue(profileFields.get(index));
			updateObj.add(updatedJson);
		}
		
		try{
			lmsField = mapper.writeValueAsString(updateObj);
			addPayload = "{\"userProfileData\":" + lmsField + ",\"sendRegistrationMail\":false,\"changePasswordAtNextLogin\":true}";
		}catch(JsonProcessingException ex){
			log.error("Exception : " + ex);
		}
		
		log.info("Adding " + email);
		responseCode = addUser(email,addPayload);
		if(responseCode==201){
			log.info("Add Successful");
		}else{
			log.error("Oh No Something Wrong!!");
			log.error("Adding User Failed");
		}
		return responseCode;
	}
}
