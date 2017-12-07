package com.amazonaws.lambda.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.lambda.beans.Course;
import com.amazonaws.lambda.beans.CourseEnrollment;
import com.amazonaws.lambda.beans.CourseProfileBean;
import com.amazonaws.lambda.beans.CourseUserBean;
import com.amazonaws.lambda.dao.impl.LmsApiCallerCourseImpl;

public class LmsApiCallerCourseServiceImpl {
	
	public List<Course> getAllCourses(){
		LmsApiCallerCourseImpl lmsApiCallerCourseImpl = new LmsApiCallerCourseImpl();
		return lmsApiCallerCourseImpl.getAllCourses();
	}
	
	public List<CourseEnrollment> getCourseAllEnrollment(String courseId){
		LmsApiCallerCourseImpl lmsApiCallerCourseImpl = new LmsApiCallerCourseImpl();
		return lmsApiCallerCourseImpl.getCourseAllEnrollment(courseId);
	}
	
	public List<String> getNotStartedUsers(String courseId){
		List<String> notStartedUsers = new ArrayList<String>();
		
		List<CourseEnrollment> allEnrollment = getCourseAllEnrollment(courseId);
		
		for(CourseEnrollment courseEnrollment: allEnrollment){
			CourseUserBean courseUser = courseEnrollment.getUser();
			CourseProfileBean courseUserProfile = null;
			String courseUserStatus = "";
			
			if(courseUser != null){
				courseUserStatus = courseUser.getUserStatus();
			}
			if(courseUser != null){
				courseUserProfile = courseUser.getProfile_Basic();
			}
			String courseStatus = courseEnrollment.getCourseStatus();
			
			if(courseStatus.equals("Not Started") && courseUserStatus.equals("Active")){
				notStartedUsers.add(courseUserProfile.getEmailId());
			}
		}
		
		return notStartedUsers;
	}
	
	public List<String> getNotStartedUsersWithDueDateExpires(String courseId){
		List<String> expireDuesDateUsers = new ArrayList<String>();
		
		List<CourseEnrollment> allEnrollment = getCourseAllEnrollment(courseId);
		
		for(CourseEnrollment courseEnrollment: allEnrollment){
			CourseUserBean courseUser = courseEnrollment.getUser();
			CourseProfileBean courseUserProfile = null;
			String courseUserStatus = "";
			
			if(courseUser != null){
				courseUserStatus = courseUser.getUserStatus();
			}
			if(courseUser != null){
				courseUserProfile = courseUser.getProfile_Basic();
			}
			String courseStatus = courseEnrollment.getCourseStatus();
			
			if(courseStatus.equals("Not Started") && courseUserStatus.equals("Active")){
				if(checkForExpiry(courseEnrollment.getDueDate())){
					expireDuesDateUsers.add(courseUserProfile.getEmailId());
				}
			}
		}
		
		return expireDuesDateUsers;
	}
	
	public boolean checkForExpiry(String dueDate){
		long res = compareDate(dueDate);
		
		if(res > 0L){
			return true;
		}
		return false;
	}
	
	public long compareDate(String dueDate){
		String currentDate = null;
		
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Date date = new Date();
		
		currentDate = sdf.format(date);
			
	    Date due = null;
	    Date current = null;
	    long elapsed = 0L;
	    
	    try{
	    	due = sdf.parse(dueDate);
	    	current = sdf.parse(currentDate);
	    }catch(ParseException e){
		   System.out.println(e);
		   try{
			   currentDate = dateFormat.format(date);
			   due = dateFormat.parse(dueDate);
		       current = dateFormat.parse(currentDate);
		   }catch(ParseException ee){
			   System.out.println(ee);
		   }
	    }
	    
	    elapsed = current.getTime() - due.getTime();
	    
	   /* System.out.println("due = " + dueDate);
	    System.out.println("curr = " + currentDate);
	    System.out.println(elapsed);*/
	    
		return elapsed;
	}
}
