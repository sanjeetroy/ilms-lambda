package com.amazonaws.lambda.util;

import java.io.BufferedReader;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.amazonaws.lambda.dao.impl.ComparatorImpl;
import com.amazonaws.lambda.domain.AdUser;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import com.amazonaws.services.s3.model.S3Object;

public class CsvHandler {
	
	static final Logger log = LogManager.getLogger(CsvHandler.class);
	/*public ArrayList<AdUser> parse(S3Object response){
		ArrayList<AdUser> adUsers = new ArrayList<AdUser>();
		
		BufferedReader br = null;
        
        try {
        	br = new BufferedReader(new InputStreamReader(response.getObjectContent()));
			BeanListProcessor<AdUser> rowProcessor = new BeanListProcessor<AdUser>(AdUser.class);

		    CsvParserSettings parserSettings = new CsvParserSettings();
		    parserSettings.setRowProcessor(rowProcessor);
		    parserSettings.setHeaderExtractionEnabled(true);

		    CsvParser parser = new CsvParser(parserSettings);
		    parser.parse(br);

		    adUsers = new ArrayList<AdUser>(rowProcessor.getBeans());
		    
		}catch (Exception e) {
			log("Exception: " + e);
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				log("Exception: "+ ex);
			}
		}
		return adUsers;
	}*/
	
	public ArrayList<AdUser> parse(BufferedReader br){
		ArrayList<AdUser> adUsers = new ArrayList<AdUser>();
		
        
        try {
			BeanListProcessor<AdUser> rowProcessor = new BeanListProcessor<AdUser>(AdUser.class);

		    CsvParserSettings parserSettings = new CsvParserSettings();
		    parserSettings.setRowProcessor(rowProcessor);
		    parserSettings.setHeaderExtractionEnabled(true);

		    CsvParser parser = new CsvParser(parserSettings);
		    parser.parse(br);

		    adUsers = new ArrayList<AdUser>(rowProcessor.getBeans());
		    
		}catch (Exception e) {
			log.error("Exception: " + e);
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				log.error("Exception: "+ ex);
			}
		}
		return adUsers;
	}
}
