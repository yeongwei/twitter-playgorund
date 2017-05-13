package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Twitter {
	private final static String CONSUMER_KEY = "pE2WI2VqFPA1awgwieQYd8wOD";
	private final static String CONSUMER_SECRET = "sLW9DBkXmAVXSKacHAzYDgtWCnbI0G6awBasHaDKBCIezukgfh";
	private final static String API_HOST = "api.twitter.com";
	private final static String TOKEN_URL = "https://" + API_HOST + "/oauth2/token";
	private final static String AUTH_BODY = "grant_type=client_credentials";
	private final static String UTF_8 = "UTF-8";
	private final static String ACCESS_TOKEN = "access_token";
	private final static Logger LOGGER = Logger.getLogger(Twitter.class.getName());
	
	private String getEncodedCredentials() throws Exception {
		String encodedConsumerKey = URLEncoder.encode(CONSUMER_KEY, UTF_8);
		String encodedConsumerSecret = URLEncoder.encode(CONSUMER_SECRET, UTF_8);
		return new String(Base64.getEncoder().encode(
				(encodedConsumerKey + ":" + encodedConsumerSecret).getBytes(UTF_8)));
	}
	
	private boolean write(HttpsURLConnection connection, String body) {
		try {
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			wr.write(body);
			wr.flush();
			wr.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	private InputStream read(HttpsURLConnection connection) {
		try {
			return connection.getInputStream();
		} catch (Exception ex) {
			return connection.getErrorStream();
		}
	}
	
	/**
	 * http://www.coderslexicon.com/demo-of-twitter-application-only-oauth-authentication-using-java/
	 * https://github.com/Jasig/EsupTwitter/blob/master/src/main/java/org/esupportail/twitter/services/OAuthTwitterApplicationOnlyService.java
	 * @throws Exception
	 */
	protected String getBearerToken() throws Exception {
		URL url = new URL(TOKEN_URL);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		connection.setRequestProperty("Authorization", "Basic" + " " + getEncodedCredentials());
		Boolean status = write(connection, AUTH_BODY);
		InputStream in = read(connection);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer response = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) response.append(line);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> result = mapper.readValue(response.toString(), Map.class);
		return result.get(ACCESS_TOKEN);
	}
	
	public static void main(String[] args) throws Exception {
		LOGGER.info(new Twitter().getBearerToken());
	}
}
