package com.njupt.stitp.server.model;

import java.sql.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class APP {

	private int id;
	private User user;
	private double APPUseTime;
	private String aPPName;
	private Date addDate;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public double getAPPUseTime() {
		return APPUseTime;
	}
	public void setAPPUseTime(double aPPUseTime) {
		APPUseTime = aPPUseTime;
	}
	public String getaPPName() {
		return aPPName;
	}
	public void setaPPName(String aPPName) {
		this.aPPName = aPPName;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

}
