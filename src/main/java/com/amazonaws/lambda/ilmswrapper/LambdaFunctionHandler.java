package com.amazonaws.lambda.ilmswrapper;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.amazonaws.lambda.dao.impl.ComparatorImpl;
import com.amazonaws.lambda.domain.AdUser;
import com.amazonaws.lambda.util.CsvHandler;
import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class LambdaFunctionHandler implements RequestHandler<S3Event, String> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    private ArrayList<AdUser> adUsers = new ArrayList<AdUser>();
    
    static final Logger log = LogManager.getLogger(LambdaFunctionHandler.class);
    
    private int noOps = 1;
    public LambdaFunctionHandler() {}

    // Test purpose only.
    LambdaFunctionHandler(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public String handleRequest(S3Event event, Context context) {
    	//AwsLogger awsLogger = new AwsLogger(context);
    	log.info("Received event: " + event);

        // Get the object from the event and show its content type
        String bucket = event.getRecords().get(0).getS3().getBucket().getName();
        String key = event.getRecords().get(0).getS3().getObject().getKey();
        try {
            S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
            String contentType = response.getObjectMetadata().getContentType();
            
            log.info("Processing File Name: " + key + " ....");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getObjectContent()));
            String opStatus = br.readLine();
            int noOpsStatus = 1;
            
            try{
            	noOpsStatus = Integer.parseInt(opStatus.split("=")[1]);
            }catch(ArrayIndexOutOfBoundsException e){
            	noOpsStatus = 1;
            }
            
            if(noOpsStatus==0){
            	noOps = 0;
            }else{
            	noOps = 1;
            }
            
            log.debug("noOpsStatus => " + opStatus);
            log.info("noOpsStatus => " + noOpsStatus);
            setAdUsers(br);
            callComparator();
            
            return contentType;
        }catch (Exception e) {
            e.printStackTrace();
            context.getLogger().log(String.format(
                "Error getting object %s from bucket %s. Make sure they exist and"
                + " your bucket is in the same region as this function.", key, bucket));
            try{
            	throw e;
            }catch(IOException ex){
            	log.error("Exception = " + 	ex);
            }
        }
        return "";
    }
    
    /*public void setAdUsers(S3Object response){
    	CsvHandler csvHandler = new CsvHandler();
    	AwsLogger.log("Csv has been converted to Java Objects");
    	adUsers = csvHandler.parse(response);
    }*/
    
    public void setAdUsers(BufferedReader response){
    	CsvHandler csvHandler = new CsvHandler();
    	log.debug("Csv has been converted to Java Objects");
    	adUsers = csvHandler.parse(response);
    }
    
    public void callComparator(){
    	ComparatorImpl comparatorImpl = new ComparatorImpl();
    	
    	if(noOps==1){
    		log.info("Calling Comparator..");
    		comparatorImpl.compare(adUsers);
    		log.info("Comparator has been Run Successfully");
    	}
    	else{
    		log.info("Calling Comparator and Updater..");
    		comparatorImpl.compareAndUpdate(adUsers);
    		log.info("Comparator & Updater has been Run Successfully");
    	}
    }
}