package com.example.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson {
	public static void main(String[] args) throws Exception {
		String sampleJson = "{\"field1\":\"This is field 1\",\"field3\":\"This is field 3\"}";
		ObjectMapper objectMapper = new ObjectMapper();
		Sample sample = objectMapper.readValue(sampleJson, Sample.class);
		System.out.println("field1: " + sample.getField1());
		System.out.println("field2: " + sample.getField2());
		System.out.println("field3: " + sample.getField3());
	} 
}

class Sample {
	private String field1;
	private String field2;
	private String field3;
	
	public String getField1() {
		return field1;
	}
	
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	public String getField2() {
		return field2;
	}
	
	public void setField2(String field2) {
		this.field2 = field2;
	}
	
	public String getField3() {
		return field3;
	}
	
	public void setField3(String field3) {
		this.field3 = field3;
	}
}