package com.amazonaws.lambda.dao;

import java.util.ArrayList;

import com.amazonaws.lambda.domain.AdUser;

public interface Comparator {
	
	public void compare(ArrayList<AdUser> adusers);
	public void setLmsUsers();
	public void compareAndSet(ArrayList<AdUser> adUsers);
	public void logDiffUsers();
	public void updateLms();
	//public void logFailedUpdate();
}
