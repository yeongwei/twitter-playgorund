package com.example.search;

import java.net.URLEncoder;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import com.example.Twitter;

public class SearchImpl extends Twitter {
	private final static String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json?lang=en&count=10&result_type=popular&q=";
	private final static Logger LOGGER = Logger.getLogger(SearchImpl.class
			.getName());
	
	public static void main(String[] args) throws Exception {
		SearchImpl searchImpl = new SearchImpl();
		String bearerToken = searchImpl.getBearerToken();
		HttpsURLConnection connection = searchImpl.makeGetConnection(
				bearerToken, SEARCH_URL + URLEncoder.encode("Huawei", UTF_8));
		connection.connect();
		String response = searchImpl.read(connection);
		LOGGER.info("response: " + response);
	}
}
