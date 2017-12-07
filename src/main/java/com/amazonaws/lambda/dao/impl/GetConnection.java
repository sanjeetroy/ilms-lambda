package com.amazonaws.lambda.dao.impl;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.amazonaws.lambda.common.RequestMethod;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class GetConnection {
	
	static final Logger log = LogManager.getLogger(GetConnection.class);
	
	public static HttpURLConnection getConnection(String ilmsUrl,RequestMethod method){
		
		  HttpURLConnection conn = null;
		  FileReader fr = null;
		  BufferedReader br = null;
		  
		  try{
			  //fr = new FileReader("../resources/credentials.txt");
			  
			  //br = new BufferedReader(fr);
			  String test_env = System.getenv("test");
			  log.debug("ENVORINMENT=" + test_env);
			  String apiId = "1T0vVqIwg0LO/R9Rnfzd6A==";//br.readLine();
			  String key = "aTxgwb8LW2SLVxbOWAFfeQ=="; //br.readLine();
			  String orgId = "2424"; //br.readLine();
			  
			  URL url = new URL(ilmsUrl);
			  conn = (HttpURLConnection) url.openConnection();
			  conn.setRequestMethod(method.toString());
			  
			  conn.setRequestProperty("Content-Type", "application/json");
			  conn.setRequestProperty("Accept", "application/json");
			  conn.setRequestProperty("APILoginID", apiId);
			  conn.setRequestProperty("TransactionKey", key);
			  conn.setRequestProperty("OrgID", orgId);
			  
		  }catch (MalformedURLException e) {
			 log.error("Exception " + e);
		  }catch (IOException e) {
			  log.error("Exception " + e);
		  }/*finally{
			 try{
				 br.close();
				 fr.close();
			 }catch(IOException e){
				AwsLogger.log("Exception " + e);
			 }
		  }*/
		    
		  return conn;
	}
	
	public static long getOrgId(){
		
		long orgId = 2424L;
		  return orgId;
	}
}
