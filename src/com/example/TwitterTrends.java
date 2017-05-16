package com.example;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TwitterTrends extends Twitter {
	private final static String TRENDS_LOC_URL = "https://api.twitter.com/1.1/trends/available.json";
	private final static Logger LOGGER = Logger.getLogger(TwitterTrends.class
			.getName());
	
	public static void main(String[] args) throws Exception {
		LOGGER.info("Programme has started (" + getMemoryReading() + ")");
		TwitterTrends twitterTrends = new TwitterTrends();
		String bearerToken = twitterTrends.getBearerToken();
		LOGGER.info("Got Bearer Token: " + bearerToken);
		HttpsURLConnection connection = twitterTrends.makeGetConnection(
				bearerToken, TRENDS_LOC_URL);
		connection.connect();
		LOGGER.info("Made connection to Location Trending API");
		String response = twitterTrends.read(connection);
		LOGGER.info("Got response (" + getMemoryReading() + ")");
		
		TypeReference<List<TrendingLocation>> ListOfTrendingLocation = new TypeReference<List<TrendingLocation>>(){};
		ObjectMapper objectMapper = new ObjectMapper();
		
		LOGGER.info("About to parse Json to Pojo (" + getMemoryReading() + ")");
		List<TrendingLocation> listOfTrendingLocation = objectMapper.readValue(response, ListOfTrendingLocation);
		LOGGER.info("Finished parsing Json to Pojo (" + getMemoryReading() + ")");
		
		listOfTrendingLocation = listOfTrendingLocation.subList(1, listOfTrendingLocation.size()); // Remove Worldwide
		listOfTrendingLocation.sort(new Comparator<TrendingLocation>() {
			@Override
			public int compare(TrendingLocation o1, TrendingLocation o2) {
				return o1.getCountry().compareTo(o2.getCountry());
			}
			
		});
		
		StringBuffer countries = new StringBuffer();
		for (int i = 0; i < listOfTrendingLocation.size(); i++) {
			countries.append(listOfTrendingLocation.get(i).getCountry() + " ");
		}
		LOGGER.info("countries: " + countries.toString());
	}
	
	private static String getMemoryReading() {
		Runtime runtime = Runtime.getRuntime();
		float heapSize = getMb(runtime.totalMemory());
		float heapMaxSize = getMb(runtime.maxMemory());
		float heapFreeSize = getMb(runtime.freeMemory());
		return "heapSize: " + heapSize + " heapMaxSize: " + heapMaxSize + " heapFreeSize: " + heapFreeSize;
	}
	
	private static float getMb(long bytes) {
		return bytes / (1024f * 1024f);
	}
}
