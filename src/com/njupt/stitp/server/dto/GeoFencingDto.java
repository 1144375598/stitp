package com.njupt.stitp.server.dto;

public class GeoFencingDto {
	private String username;
	private double longtitude;
	private double latitude;
	private double distance;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
