package com.amazonaws.lambda.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IlmsFieldToNumber {

private static final Map<String, Integer> mapFieldToNumber = createMap();
	
    private static Map<String, Integer> createMap()
    {
        Map<String,Integer> map = new HashMap<String,Integer>();
        
        map.put("firstName", 1);
    	map.put("middleName",2);
    	map.put("lastName",3);
    	map.put("employeeId", 4);
    	map.put("hireDate",5);
    	
    	map.put("userLanguage",6);
    	map.put("jobTitle",7);
    	map.put("address1",8);
    	map.put("address2",9);
    	map.put("city",10);
    	
    	map.put("country",11);
    	map.put("state",12);
    	map.put("province",13);  //District
    	map.put("zipCode",14);
    	map.put("emailId",15);
    	
    	map.put("password",16);
    	map.put("phoneNo",17);
    	map.put("faxNo",18);
    	map.put("region",19);
    	
    	map.put("division",20);
    	map.put("department",21);
    	map.put("company",22);
    	
    	map.put("timeZone",23);
    	map.put("customField1",24);
    	map.put("customField2",25);
    	map.put("customField3",26);
    	map.put("customField4",27);
    	
    	map.put("registrationType",31);
    	map.put("registrationDate",32);
    	map.put("supervisorName",33);
    	map.put("supervisorEmail",34);
    	
        return map;
    }
    
    public static List<Integer> convert(List<String> diffFields){
    	
    	List<Integer> numbers = new ArrayList<Integer>();
    	for(String s:diffFields){
    		numbers.add(mapFieldToNumber.get(s));
    	}
    	System.out.println(numbers.toString());
    	return numbers;
    }
    
    public static int get(String field){
    	return mapFieldToNumber.get(field);
    }
}
