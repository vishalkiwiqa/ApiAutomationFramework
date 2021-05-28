package com.MBS.Init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PayLoadConvertor {
	
	private static Logger log = LogManager.getLogger(PayLoadConvertor.class.getName());
	public static String generatePayLoadString(String fileName) throws IOException {
		
		log.info("Inside PayLoader Function");
		String filepath = System.getProperty("user.dir")+"\\DownloadData\\"+fileName;
		try {
			return new String (Files.readAllBytes(Paths.get(filepath)));
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	
}
