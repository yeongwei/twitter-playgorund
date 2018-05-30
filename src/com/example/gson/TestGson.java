package com.example.gson;

import com.example.TwitterSampleData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TestGson {
	public static void main(String[] args) {
		Gson gson = new Gson();
		
		JsonElement je = gson.fromJson(TwitterSampleData.get(), JsonElement.class);
		JsonObject jo = je.getAsJsonObject();
		
		JsonElement je1 = jo.get("statuses");
		JsonArray ja = je1.getAsJsonArray();
		
		System.out.println(ja.get(0).getAsJsonObject().get("text").getAsString());
	}
}
