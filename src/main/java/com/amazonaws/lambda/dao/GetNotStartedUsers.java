package com.amazonaws.lambda.dao;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.lambda.beans.Course;
import com.amazonaws.lambda.service.impl.LmsApiCallerCourseServiceImpl;

public class GetNotStartedUsers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LmsApiCallerCourseServiceImpl lmsApiCallerCourseServiceImpl = new LmsApiCallerCourseServiceImpl();
		List<Course> allCourse = lmsApiCallerCourseServiceImpl.getAllCourses();
		List<String > notstartedUsers = null;
		List<String > expiresUsers = null;
		
		for(Course course:allCourse){
			notstartedUsers = lmsApiCallerCourseServiceImpl.getNotStartedUsers(course.getID());
			expiresUsers = lmsApiCallerCourseServiceImpl.getNotStartedUsersWithDueDateExpires(course.getID());
			
			System.out.println(course.getName());
			System.out.println(course.getID());
			System.out.println("number of Not Started Users = " + notstartedUsers.size());
			System.out.println(Arrays.toString(notstartedUsers.toArray()));
			
			System.out.println("number of Not Started Users Whose Due Date Expires = " + expiresUsers.size());
			System.out.println(Arrays.toString(expiresUsers.toArray()));
		}
		
		
		
		
	}

}
