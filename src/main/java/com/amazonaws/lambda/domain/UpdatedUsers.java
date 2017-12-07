package com.amazonaws.lambda.domain;

import java.util.ArrayList;
import java.util.List;

public class UpdatedUsers {
	private List<String> users;

	public UpdatedUsers(){
		users = new ArrayList<String>();
	}
	
	public void add(String email){
		users.add(email);
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
	
}
