package com.example;

import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class TwitterPlaces extends Twitter {
	private final static String GEO_SEARCH_URL = "https://api.twitter.com/1.1/geo/search.json?query=";
	public static void main(String[] args) throws Exception {
		TwitterPlaces twitterPlaces  = new TwitterPlaces();
		String bearerToken = twitterPlaces.getBearerToken();
		System.out.println(bearerToken);
		HttpsURLConnection connection = twitterPlaces.makeGetConnection(bearerToken, GEO_SEARCH_URL + URLEncoder.encode("Persistent Systems Malaysia Sdn Bhd", UTF_8));
		connection.connect();
		String response = twitterPlaces.read(connection);
		System.out.print(response);
	}
}
