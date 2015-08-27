package com.njupt.stitp.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	private String username;
	private String password;
	public int getTimeOfContinuousUse() {
		return timeOfContinuousUse;
	}
	public void setTimeOfContinuousUse(int timeOfContinuousUse) {
		this.timeOfContinuousUse = timeOfContinuousUse;
	}
	public int getTimeOfContinuousListen() {
		return timeOfContinuousListen;
	}
	public void setTimeOfContinuousListen(int timeOfContinuousListen) {
		this.timeOfContinuousListen = timeOfContinuousListen;
	}
	private int timeOfContinuousUse;
	private int timeOfContinuousListen;
	private int musicVolume;
	
	@Id
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
	
	public int getMusicVolume() {
		return musicVolume;
	}
	public void setMusicVolume(int musicVolume) {
		this.musicVolume = musicVolume;
	}

}
