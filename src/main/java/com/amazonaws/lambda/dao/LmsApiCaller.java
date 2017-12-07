package com.amazonaws.lambda.dao;

import java.util.Map;

import com.amazonaws.lambda.beans.Department;
import com.amazonaws.lambda.beans.Division;
import com.amazonaws.lambda.beans.Region;
import com.amazonaws.lambda.domain.LmsUser;


public interface LmsApiCaller {
	public Map<String,LmsUser> getAllUsers();
	public LmsUser getUserById(long userId);
	public LmsUser getUserByEmailId(String email);
	public long getUserIdByEmailId(String email);
	public int updateUserProfileById(long userId,String payload);
	public int addUser(String emailId,String payload);
	public int inActivateUsers(Long id);
	
	public Map<String,Region> getAllRegions();
	public int addRegion(String region,String payload);
	
	public Map<String,Division> getAllDivisions(Long regionId);
	public int addDivision(Long regionId,String division,String payload);
	
	public Map<String,Department> getAllDepartments(Long regionId,Long divisionId);
	public int addDepartment(Long regionId,long divisionId,String department,String payload);
	/*
	public void createMultipleUser(Profile[] profile);
	public void createUser(Profile profile);
	
	public void activateUserAccountById(long userId);
	public void setNewPassword(long userId,String password,boolean changePasswordAtNextLogin);
	
	public void deActivateUserAccountById(long userId);
	public void deleteUserAccountById(long userId);
	public void purgeUserAccountById(long userId);
	
	*/
}
