package com.njupt.stitp.server.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class APP {

	private int id;
	private User user;
	private int appUseTime;
	private String appName;
	private Date addDate;
	private String icon;

	public APP() {
		user = new User();
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public int getAppUseTime() {
		return appUseTime;
	}

	public void setAppUseTime(int appUseTime) {
		this.appUseTime = appUseTime;
	}

	/*
	 * @Lob
	 * 
	 * @Column(name = "icon", columnDefinition = "BLOB", nullable = true) public
	 * byte[] getIcon() { return icon; }
	 * 
	 * public void setIcon(byte[] icon) { this.icon = icon; }
	 */

	@Lob
	@Column(name = "icon", columnDefinition = "LONGTEXT", nullable = true)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
