package com.amazonaws.lambda.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.lambda.beans.Course;
import com.amazonaws.lambda.beans.CourseEnrollment;
import com.amazonaws.lambda.beans.UserBean;
import com.amazonaws.lambda.common.RequestMethod;
import com.amazonaws.lambda.domain.LmsUser;
import com.amazonaws.lambda.util.JsonToBean;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class LmsApiCallerCourseImpl {
	static final Logger log = LogManager.getLogger(LmsApiCallerImpl.class);
	private long organisationId;
	private List<Course> courses = new ArrayList<Course>();
	private List<CourseEnrollment> courseEnrollements = new ArrayList<CourseEnrollment>(); 
	
	public LmsApiCallerCourseImpl(){
		log.debug("Organisation Id has been settted." );
		this.organisationId = GetConnection.getOrgId();
	}
	
	public List<Course> getAllCourses(){
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/courses?per_page=500";
		String next = getAllCourses(ilmsUrl);
		while(next != null){
			try{
				next = getAllCourses(next);
			}catch(RuntimeException e){
				log.error(e);
			}
		}
		return courses;
	}
	
	public String getAllCourses(String ilmsUrl){
		List<Course> bean = null;
		String next = null;
		try{
			log.debug("Trying to make a GET Request to ILMS, For All Courses");
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.GET);
			
			if (conn.getResponseCode() == 200) {
				log.debug("GET Request has been Successfully ececuted.");
				 
				log.debug("Trying to read the Json Response From ILMS.");
				next = conn.getHeaderField("next");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
				String json = br.readLine();
				
				System.out.println(" course json = " + json);
				bean = JsonToBean.jsonToAllCourse(json);
				
				for(Course course:bean){
					courses.add(course);
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
	
	public List<CourseEnrollment> getCourseAllEnrollment(String courseId){
		String ilmsUrl = "https://api.inspiredlms.com/organizations/" + this.organisationId + "/courses/" + courseId + "/enrollments?per_page=500";
		String next = getAllEnrollment(ilmsUrl);
		while(next != null){
			try{
				next = getAllEnrollment(next);
			}catch(RuntimeException e){
				log.error(e);
			}
		}
		return courseEnrollements;
	}
	
	public String getAllEnrollment(String ilmsUrl){
		List<CourseEnrollment> bean = null;
		String next = null;
		try{
			log.debug("Trying to make a GET Request to ILMS, For All Courses");
			HttpURLConnection conn = GetConnection.getConnection(ilmsUrl, RequestMethod.GET);
			
			if (conn.getResponseCode() == 200) {
				log.debug("GET Request has been Successfully ececuted.");
				 
				log.debug("Trying to read the Json Response From ILMS.");
				next = conn.getHeaderField("next");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
				String json = br.readLine();
				
				//System.out.println("Course Enroll Json = " + json);
				bean = JsonToBean.jsonToAllCourseEnrollment(json);
				
				for(CourseEnrollment courseEnrollment:bean){
					courseEnrollements.add(courseEnrollment);
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
}
