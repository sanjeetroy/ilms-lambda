package com.amazonaws.lambda.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;

import com.amazonaws.lambda.common.RequestMethod;
import com.amazonaws.lambda.dao.impl.GetConnection;
import com.amazonaws.lambda.dao.impl.LmsApiCallerImpl;
import com.amazonaws.lambda.domain.LmsUser;


public class ActivateAll {
	
	public static void activate_user(long id){
		String ilmsUrl = "https://api.inspiredlms.com/organizations/2424" + "/users/" + id + "/Activate";
		
		try{
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.PUT);
			
			if (conn.getResponseCode() != 204) {
				System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}else{
				System.out.println("Activated " + id);
			}
			  
			
			conn.disconnect();
			
		}catch(IOException e){
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LmsApiCallerImpl lmsApiCallerImpl = new LmsApiCallerImpl();
		Map<String,LmsUser> usersAll = lmsApiCallerImpl.getAllUsers();
		
		String csvFile = "/Users/sanjeet.roy/projects/ilms/csvs/inac.csv";
	    String line = "";
	    String cvsSplitBy = ",";
	    
	    System.out.println("gl = " + usersAll.size());
	    System.out.println(usersAll.get("produte.roy@appdirect.com").getId());
	    
	    //activate_user(usersAll.get("produte.roy@appdirect.com").getId());
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	line = br.readLine();
	    	String[] users = line.split(cvsSplitBy);
	    	System.out.println(line);
            System.out.println(users.length);
            
            for(String user:users){
            	LmsUser lmsUser = usersAll.get(user.trim());
            	System.out.println(user+ " = " + lmsUser.getId());
            	try{
            		activate_user(lmsUser.getId());
            	}catch(RuntimeException e){
            		System.out.println("User " + user + " is already activated");
            	}
            }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}

}
