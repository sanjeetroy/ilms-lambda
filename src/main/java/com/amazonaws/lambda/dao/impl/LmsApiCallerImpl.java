package com.amazonaws.lambda.dao.impl;

import java.io.BufferedReader;



import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.amazonaws.lambda.beans.Department;
import com.amazonaws.lambda.beans.Division;
import com.amazonaws.lambda.beans.Region;
import com.amazonaws.lambda.beans.UserBean;
import com.amazonaws.lambda.common.RequestMethod;
import com.amazonaws.lambda.dao.LmsApiCaller;
import com.amazonaws.lambda.domain.LmsUser;
import com.amazonaws.lambda.util.JsonToBean;
import com.amazonaws.lambda.util.ObjectConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class LmsApiCallerImpl implements LmsApiCaller {
	static final Logger log = LogManager.getLogger(LmsApiCallerImpl.class);
	private long organisationId;
	private static Map<String,LmsUser> lmsUsers = new TreeMap<String,LmsUser>(String.CASE_INSENSITIVE_ORDER);
	private  Map<String,Region> lmsRegions = new TreeMap<String,Region>(String.CASE_INSENSITIVE_ORDER);
	private  Map<String,Division> lmsDivisions = new TreeMap<String,Division>(String.CASE_INSENSITIVE_ORDER);
	private  Map<String,Department> lmsDepartments = new TreeMap<String,Department>(String.CASE_INSENSITIVE_ORDER);
	
	
	public LmsApiCallerImpl(long organisationId){
		log.debug("Organisation Id has been settted ");
		this.organisationId = organisationId;
	}
	
	public LmsApiCallerImpl(){
		log.debug("Organisation Id has been settted." );
		this.organisationId = GetConnection.getOrgId();
	}
	
	public void setMyOrganisationId(long orgId){
		log.debug("Organisation Id has been settted ");
		this.organisationId = orgId;
	}
	
	public Map<String,LmsUser> getAllUsers(){
		//String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/groups/6000/members?per_page=500";
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/users?per_page=500";
		String next = getAllUsers(ilmsUrl);
		while(next != null){
			try{
				next = getAllUsers(next);
			}catch(RuntimeException e){
				log.error(e);
			}
		}
		return lmsUsers;
	}
	
	public String getAllUsers(String ilmsUrl){
		List<UserBean> bean = null;
		String next = null;
		try{
			log.debug("Trying to make a GET Request to ILMS, For All Users");
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.GET);
			
			if (conn.getResponseCode() == 200) {
				log.debug("GET Request has been Successfully ececuted.");
				 
				log.debug("Trying to read the Json Response From ILMS.");
				next = conn.getHeaderField("next");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
				String json = br.readLine();
				
				bean = JsonToBean.jsonToAllUserBean(json);
				addToDict(bean);
				
			}else{
				log.error("Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage());
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch(IOException e){
			log.error("Exception " + e);
		}
		return next;
	}
	
	public void addToDict(List<UserBean> bean){
		int size = bean.size();
		for(int i=0;i<size;i++){
			LmsUser lmsUser = ObjectConverter.userBeanToLmsUser(bean.get(i));
			
			if(lmsUser.getProfile().getEmailId() != null){
				lmsUsers.put(lmsUser.getProfile().getEmailId().trim().replaceAll(" +", " "), lmsUser);
			}else{				
				lmsUsers.put(lmsUser.getProfile().getEmailId(), lmsUser);
			}
		}
	}
	
	public LmsUser getUserByEmailId(String email){
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/users?F015=" + email;
		UserBean bean = null;
		
		try{
			log.debug("Trying to make a GET Request to ILMS, For User: " + email);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.GET);
			
			if (conn.getResponseCode() == 200) {
				log.debug("GET Request has been Successfully executed.");
				
				log.debug("Trying to read the Json Respponse From ILMS.");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String json = br.readLine();
				if(json.contains("{")){
					
					try{
						bean = JsonToBean.jsonToAllUserBean(json).get(0);
					}catch(ArrayIndexOutOfBoundsException e){
						log.error("Exception " + e);
					}
				}else{
					log.info("User with emialId:" + email + " is NOT FOUND.");
				}
				
			}else{
				log.error("Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage());
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
			
		}catch(IOException e){
			log.error("Exception " + e);
		}
		return ObjectConverter.userBeanToLmsUser(bean);
	}
	
	public long getUserIdByEmailId(String email){
		LmsUser lsmUser = getUserByEmailId(email);
		return lsmUser.getId();
	}
	
	public LmsUser getUserById(long userId){
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/users/" + userId;
		UserBean bean = null;
		
		try{
			log.debug("Trying to make a GET Request to ILMS, For User: " + userId);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.GET);
			
			if (conn.getResponseCode() == 200) {
				log.debug("GET Request has been Successfully executed.");
				log.debug("Trying to read the Json Respponse From ILMS.");    
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String json = br.readLine();
				bean = JsonToBean.jsonToUserBean(json);
				
			}else{
				log.error("Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage());
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
			
		}catch(IOException e){
			log.error("Exception " + e);
		}
		return ObjectConverter.userBeanToLmsUser(bean);
	}
	
	public int updateUserProfileById(long id,String payload){
		int responseCode = -1;
		
		UserBean bean = null;
		
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/users/" + id;
		
		try{
			log.debug("Trying to Update the information of User with userId= " + id);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.PUT);
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			if (conn.getResponseCode() != 204) {
				log.info("Update Fails for userId "+ id) ;
				log.error("Failed : HTTP error code : " + conn.getResponseCode());
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " "+ conn.getErrorStream());
			}else{
				log.info("User " + id  + " has been successfully Updated");
			}
		  
			responseCode = conn.getResponseCode();
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch (IOException e) {
			log.error("Exception " + e);
		  }
		
		  return responseCode;
	}
	
	public int addUser(String emailId,String payload){
		int responseCode = -1;
		
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/users";
		
		try{
			log.debug("Trying to Add the User : " + emailId);
			log.debug("payload = " + payload);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.POST);
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			if (conn.getResponseCode() != 201) {
				log.info("Not able to Add user:" + emailId) ;
				log.error("Failed : HTTP error code : " + conn.getResponseCode());
				
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
				String output = br.readLine();
				log.error(output);
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " "+ conn.getResponseMessage());
			}else{
				log.info("User " + emailId  + " has been successfully Added");
			}
		  
			responseCode = conn.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		    log.info("Ilms Id of the newly Added user = " + br.readLine());
		  
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch (MalformedURLException e) {
			e.printStackTrace();
		  }catch (IOException e) {
			log.error("Exception " + e);
		  }
		
		  return responseCode;
		
	}
	
	public Map<String,Region> getAllRegions(){
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/regions?per_page=500";
		String next = getAllRegions(ilmsUrl);
		while(next != null){
			next = getAllRegions(next);
		}
		return lmsRegions;
	}
	
	public String getAllRegions(String ilmsUrl){
		List<Region> bean = null;
		
		String next = null;
		try{
			log.debug("Trying to make a GET Request to ILMS, For All Regions");
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.GET);
			
			if (conn.getResponseCode() == 200) {
				log.debug("GET Request has been Successfully ececuted.");
				 
				log.debug("Trying to read the Json Response From ILMS.");
				next = conn.getHeaderField("next");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
				String json = br.readLine();
				
				bean = JsonToBean.jsonToAllRegion(json);
				
				for(Region region:bean){
					if(region.getName() != null){
						lmsRegions.put(region.getName().trim().replaceAll(" +", " "), region);
					}else{
						lmsRegions.put(region.getName(), region);
					}
				}
			}else{
				log.error("Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage());
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch(IOException e){
			log.error("Exception " + e);
		}
		return next;
	}
	
	public int addRegion(String region,String payload){
		int responseCode = -1;
		
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/regions";
		
		try{
			log.debug("Trying to Add the Region : " + region);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.POST);
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			if (conn.getResponseCode() != 201) {
				log.info("Not able to Add Region:" + region) ;
				log.error("Failed : HTTP error code : " + conn.getResponseCode());
				
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " "+ conn.getResponseMessage());
			}else{
				log.info("Region " + region  + " has been successfully Added");
			}
		  
			responseCode = conn.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		    log.info("Ilms Id of the newly Added Region = " + br.readLine());
		  
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch (MalformedURLException e) {
			e.printStackTrace();
		  }catch (IOException e) {
			log.error("Exception " + e);
		  }
		
		  return responseCode;
		
	}
	
	public Map<String,Division> getAllDivisions(Long regionId){
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/regions/" + regionId + "/divisions?per_page=500" ;
		String next = getAllDivisions(ilmsUrl,regionId);
		while(next != null){
			next = getAllDivisions(next,regionId);
		}
		return lmsDivisions;
	}
	
	public String getAllDivisions(String ilmsUrl,long regionId){
		List<Division> bean = null;
		
		String next = null;
		try{
			log.debug("Trying to make a GET Request to ILMS, For All Divisions of " + regionId);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.GET);
			
			if (conn.getResponseCode() == 200) {
				log.debug("GET Request has been Successfully ececuted.");
				 
				log.debug("Trying to read the Json Response From ILMS.");
				next = conn.getHeaderField("next");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
				String json = br.readLine();
				bean = JsonToBean.jsonToAllDivision(json);
				
				for(Division division:bean){
					if(division.getName() != null){
						lmsDivisions.put(division.getName().trim().replaceAll(" +", " "), division);
					}else{
						lmsDivisions.put(division.getName(), division);
					}
				}
			}else{
				log.error("Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage());
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch(IOException e){
			log.error("Exception " + e);
		}
		return next;
	}
	
	public int addDivision(Long regionId,String division,String payload){
		int responseCode = -1;
		
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/regions/" + regionId + "/divisions";
		
		try{
			log.debug("Trying to Add the Division : " + division + " in regionId = " + regionId);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.POST);
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			if (conn.getResponseCode() != 201) {
				log.info("Not able to Add Division:" + division) ;
				log.error("Failed : HTTP error code : " + conn.getResponseCode());
				
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " "+ conn.getResponseMessage());
			}else{
				log.info("Division " + division  + " has been successfully Added");
			}
		  
			responseCode = conn.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		    log.info("Ilms Id of the newly Added Division = " + br.readLine());
		  
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch (MalformedURLException e) {
			e.printStackTrace();
		  }catch (IOException e) {
			log.error("Exception " + e);
		  }
		
		  return responseCode;
		
	}
	
	public Map<String,Department> getAllDepartments(Long regionId,Long divisionId){
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/regions/" + regionId + "/divisions/" + divisionId + "/departments?per_page=500" ;
		String next = getAllDepartments(ilmsUrl);
		while(next != null){
			next = getAllDepartments(next);
		}
		return lmsDepartments;
	}
	
	public String getAllDepartments(String ilmsUrl){
		List<Department> bean = null;
		
		String next = null;
		try{
			log.debug("Trying to make a GET Request to ILMS, For All Departments");
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.GET);
			
			if (conn.getResponseCode() == 200) {
				log.debug("GET Request has been Successfully ececuted.");
				 
				log.debug("Trying to read the Json Response From ILMS.");
				next = conn.getHeaderField("next");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
				String json = br.readLine();
				
				bean = JsonToBean.jsonToAllDepartment(json);
				
				for(Department department:bean){
					if(department.getName() != null){
						lmsDepartments.put(department.getName().trim().replaceAll(" +", " "), department);
					}else{
						lmsDepartments.put(department.getName(), department);
					}
				}
			}else{
				log.error("Failed : HTTP error code : " + conn.getResponseCode() + " " + conn.getResponseMessage());
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch(IOException e){
			log.error("Exception " + e);
		}
		return next;
	}
	
	public int addDepartment(Long regionId,long divisionId,String department,String payload){
		int responseCode = -1;
		
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/regions/" + regionId + "/divisions/" + divisionId + "/departments";
		
		try{
			log.debug("Trying to Add the Department : " + department + " in division = " + divisionId + " in region = " + regionId);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.POST);
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			if (conn.getResponseCode() != 201) {
				log.info("Not able to Add Department:" + department) ;
				log.error("Failed : HTTP error code : " + conn.getResponseCode());
				
				LogResponseCode.log(conn.getResponseCode());
				//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " "+ conn.getResponseMessage());
			}else{
				log.info("Department " + department  + " has been successfully Added");
			}
		  
			responseCode = conn.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		    log.info("Ilms Id of the newly Added Department = " + br.readLine());
		  
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch (MalformedURLException e) {
			e.printStackTrace();
		  }catch (IOException e) {
			log.error("Exception " + e);
		  }
		
		  return responseCode;
		
	}
	
	public int inActivateUsers(Long id){
		int responseCode = -1;
		
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/users/" + id + "/Activate";
		
		try{
			log.debug("Trying to InActivate the User : " + id);
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.DELETE);
		
			responseCode = conn.getResponseCode();
			if (responseCode != 204) {
				log.info("Not able to InActivate user:" + id) ;
				log.error("Failed : HTTP error code : " + responseCode);
		
				//throw new RuntimeException("Failed : HTTP error code : " + responseCode + " "+ conn.getResponseMessage());
			}else{
				log.info("User " + id  + " has been successfully InActivated");
			}
		  
			log.debug("Trying to Disconnect from ILMS.");
			conn.disconnect();
			log.debug("Successfully Disconnected from ILMS.");
		}catch (MalformedURLException e) {
			e.printStackTrace();
		  }catch (IOException e) {
			log.error("Exception " + e);
		  }
		return responseCode;
	}
}
