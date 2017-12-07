package com.amazonaws.lambda.service;

import java.util.ArrayList;
import java.util.Map;

import com.amazonaws.lambda.domain.LmsUser;
import com.amazonaws.lambda.domain.NewUser;

public interface LmsApiCallerService {
	public long getUserIdByEmailId(String email);
	
	public Map<String,LmsUser> getAllUsers();
	public LmsUser getUserById(long userId);
	public LmsUser getUserByEmailId(String email);
	
	public int updateUserProfileById(long userId,String payload);
	public int updateUserProfileByEmailId(String email,String payload);
	
	public void addUsers(ArrayList<NewUser> newUsers);
	public int addUser(NewUser newUser);
	
	public void inActivateUsers(Map<String,Long> lmsToBeInActivedUsers);
//	public int updateUserProfileBeanByEmailId(String email,ProfileBean profileBean);
//	public int updateUserProfileByEmailId(String email,Profile profile);
}
