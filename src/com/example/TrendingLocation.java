package com.example;

class PlaceType {
	private String code;
	private String name;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

public class TrendingLocation {
	private String name;
	private PlaceType placeType;
	private String url;
	private String parentid;
	private String country;
	private String woeid;
	private String countryCode;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public PlaceType getPlaceType() {
		return placeType;
	}
	
	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getParentid() {
		return parentid;
	}
	
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getWoeid() {
		return woeid;
	}
	
	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
