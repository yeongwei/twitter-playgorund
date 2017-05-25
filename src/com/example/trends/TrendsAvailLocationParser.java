package com.example.trends;

import java.io.File;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Evaluation for https://dev.twitter.com/rest/reference/get/trends/available
 * parse JSON into Java Objects
 */
public class TrendsAvailLocationParser {
	private static Logger LOGGER = Logger.getLogger(TrendsAvailLocationParser.class.getName());
	public static void main(String[] args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		File trendingLocationFile = new File("D:\\development\\androidSpecialization\\capstone\\twitter\\src\\com\\example\\trends\\TrendsAvailLocation.json");
		TrendsAvailLocation trendingLocation = mapper.readValue(trendingLocationFile, TrendsAvailLocation.class);
		LOGGER.info(trendingLocation.toString());
		assert trendingLocation.getName() == "Bistrol";
	}
}
