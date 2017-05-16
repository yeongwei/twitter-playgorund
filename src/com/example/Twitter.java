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
	private final static String TOKEN_URL = "https://" + API_HOST
			+ "/oauth2/token";
	private final static String INVALID_TOKEN_URL = "https://" + API_HOST
			+ "/oauth2/invalidate_token";
	private final static String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json?lang=en&count=10&result_type=popular&q=";
	private final static String AUTH_BODY = "grant_type=client_credentials";
	protected final static String UTF_8 = "UTF-8";
	private final static String ACCESS_TOKEN = "access_token";
	private final static Logger LOGGER = Logger.getLogger(Twitter.class
			.getName());
	private ObjectMapper mapper = new ObjectMapper();

	private String getEncodedCredentials() throws Exception {
		String encodedConsumerKey = URLEncoder.encode(CONSUMER_KEY, UTF_8);
		String encodedConsumerSecret = URLEncoder
				.encode(CONSUMER_SECRET, UTF_8);
		return new String(Base64.getEncoder().encode(
				(encodedConsumerKey + ":" + encodedConsumerSecret)
						.getBytes(UTF_8)));
	}

	private HttpsURLConnection makePostConnection(String urlString)
			throws Exception {
		URL url = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		connection.setRequestProperty("Authorization", "Basic" + " "
				+ getEncodedCredentials());
		return connection;
	}

	protected HttpsURLConnection makeGetConnection(String bearerToken,
			String urlString) throws Exception {
		URL url = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection) url
				.openConnection();
		connection.setRequestMethod("GET");
		// connection.setRequestProperty("Accept-Encoding", "gzip"); // This
		// will return gzip result
		connection.setRequestProperty("Authorization", "Bearer" + " "
				+ bearerToken);
		return connection;
	}

	private boolean write(HttpsURLConnection connection, String body) {
		try {
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					connection.getOutputStream()));
			wr.write(body);
			wr.flush();
			wr.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	protected String read(HttpsURLConnection connection) throws Exception {
		InputStream in;
		try {
			in = connection.getInputStream();
		} catch (Exception ex) {
			in = connection.getErrorStream();
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer response = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null)
			response.append(line);
		br.close();
		return response.toString();
	}

	/**
	 * http://www.coderslexicon.com/demo-of-twitter-application-only-oauth-
	 * authentication-using-java/
	 * https://github.com/Jasig/EsupTwitter/blob/master
	 * /src/main/java/org/esupportail
	 * /twitter/services/OAuthTwitterApplicationOnlyService.java
	 * 
	 * @throws Exception
	 */
	protected String getBearerToken() throws Exception {
		HttpsURLConnection connection = makePostConnection(TOKEN_URL);
		write(connection, AUTH_BODY);
		String response = read(connection);
		Map<String, String> result = mapper.readValue(response, Map.class);
		return result.get(ACCESS_TOKEN);
	}

	protected Boolean invalidateBearerToken(String bearerToken)
			throws Exception {
		HttpsURLConnection connection = makePostConnection(INVALID_TOKEN_URL);
		write(connection, ACCESS_TOKEN + "=" + bearerToken);
		String response = read(connection);
		Map<String, String> result = mapper.readValue(response, Map.class);
		if (result.get(ACCESS_TOKEN).equals(bearerToken))
			return true;
		else
			return false;
	}

	protected String search(String bearerToken, String searchPhrase)
			throws Exception {
		String fullUrl = SEARCH_URL + URLEncoder.encode(searchPhrase, UTF_8);
		HttpsURLConnection connection = makeGetConnection(bearerToken, fullUrl);
		connection.connect();
		String response = read(connection);
		return response;
	}

	public static void main(String[] args) throws Exception {
		Twitter twitter = new Twitter();
		String bearerToken = twitter.getBearerToken();
		LOGGER.info(bearerToken);
		String searchResponse = twitter.search(bearerToken, "malaysia");
		LOGGER.info(searchResponse);
		Boolean status = twitter.invalidateBearerToken(bearerToken);
		LOGGER.info(String.valueOf(status));
	}
}
