package com.example;

import java.io.File;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TrendingLocationParser {
	private static Logger LOGGER = Logger.getLogger(TrendingLocationParser.class.getName());
	public static void main(String[] args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		File trendingLocationFile = new File("D:\\development\\androidSpecialization\\capstone\\twitter\\src\\com\\example\\trendingLocation.txt");
		TrendingLocation trendingLocation = mapper.readValue(trendingLocationFile, TrendingLocation.class);
		LOGGER.info(trendingLocation.toString());		
	}
}
