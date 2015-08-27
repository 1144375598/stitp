package com.njupt.stitp.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	private int id;
	private String username;
	private String password;
	private double timeOfContinuousUse;
	private double timeOfContinuousListen;
	private int musicVolume;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getTimeOfContinuousUse() {
		return timeOfContinuousUse;
	}
	public void setTimeOfContinuousUse(double timeOfContinuousUse) {
		this.timeOfContinuousUse = timeOfContinuousUse;
	}
	public double getTimeOfContinuousListen() {
		return timeOfContinuousListen;
	}
	public void setTimeOfContinuousListen(double timeOfContinuousListen) {
		this.timeOfContinuousListen = timeOfContinuousListen;
	}
	public int getMusicVolume() {
		return musicVolume;
	}
	public void setMusicVolume(int musicVolume) {
		this.musicVolume = musicVolume;
	}

}
