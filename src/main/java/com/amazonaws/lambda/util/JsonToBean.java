package com.amazonaws.lambda.util;

import java.util.Arrays;


import java.util.List;

import com.amazonaws.lambda.beans.Course;
import com.amazonaws.lambda.beans.CourseEnrollment;
import com.amazonaws.lambda.beans.Department;
import com.amazonaws.lambda.beans.Division;
import com.amazonaws.lambda.beans.Region;
import com.amazonaws.lambda.beans.UserBean;
import com.amazonaws.lambda.dao.impl.ComparatorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class JsonToBean {
	static final Logger log = LogManager.getLogger(JsonToBean.class);
	
	public static UserBean jsonToUserBean(String json){
	
		ObjectMapper mapper = new ObjectMapper();
		UserBean bean = null;
		
		try{
			bean = mapper.readValue(json, UserBean.class);
		}catch(Exception e){
			log.error("Exception " + e);
		}
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static List<UserBean> jsonToAllUserBean(String json){
		
		ObjectMapper mapper = new ObjectMapper();
		List<UserBean> bean = null;
		
		try{
			bean = Arrays.asList(mapper.readValue(json, UserBean[].class));
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static Course jsonToCourse(String json){
		
		ObjectMapper mapper = new ObjectMapper();
		Course bean = null;
		
		try{
			bean = mapper.readValue(json, Course.class);
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}

	public static List<Course> jsonToAllCourse(String json){
		
		ObjectMapper mapper = new ObjectMapper();
		List<Course> bean = null;
		
		try{
			bean = Arrays.asList(mapper.readValue(json, Course[].class));
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static CourseEnrollment jsonToCourseEnrollment(String json){
		ObjectMapper mapper = new ObjectMapper();
		CourseEnrollment bean = null;
		
		try{
			bean = mapper.readValue(json, CourseEnrollment.class);
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static List<CourseEnrollment> jsonToAllCourseEnrollment(String json){
		ObjectMapper mapper = new ObjectMapper();
		List<CourseEnrollment> bean = null;
		
		try{
			bean = Arrays.asList(mapper.readValue(json, CourseEnrollment[].class));
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static Region jsonToRegion(String json){
		ObjectMapper mapper = new ObjectMapper();
		Region bean = null;
		
		try{
			bean = mapper.readValue(json, Region.class);
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static List<Region> jsonToAllRegion(String json){
		ObjectMapper mapper = new ObjectMapper();
		List<Region> bean = null;
		
		try{
			bean = Arrays.asList(mapper.readValue(json, Region[].class));
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static Division jsonToDivision(String json){
		ObjectMapper mapper = new ObjectMapper();
		Division bean = null;
		
		try{
			bean = mapper.readValue(json, Division.class);
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static List<Division> jsonToAllDivision(String json){
		ObjectMapper mapper = new ObjectMapper();
		List<Division> bean = null;
		
		try{
			bean = Arrays.asList(mapper.readValue(json, Division[].class));
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static Department jsonToDepartment(String json){
		ObjectMapper mapper = new ObjectMapper();
		Department bean = null;
		
		try{
			bean = mapper.readValue(json, Department.class);
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
	
	public static List<Department> jsonToAllDepartment(String json){
		ObjectMapper mapper = new ObjectMapper();
		List<Department> bean = null;
		
		try{
			bean = Arrays.asList(mapper.readValue(json, Department[].class));
		}catch(Exception e){
			log.error("Exception " + e);
		}
		
		log.debug("Json response has been Converted to java Object");
		return bean;
	}
}
