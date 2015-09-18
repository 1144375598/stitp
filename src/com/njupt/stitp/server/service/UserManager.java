package com.njupt.stitp.server.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.njupt.stitp.server.dao.UserDao;
import com.njupt.stitp.server.model.User;

@Component
public class UserManager {
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public boolean exists(User user){
		if(userDao.checkUserExistsWithName(user)){
			return true;
		}
		return false;
	}
	
	public void updateCid(User user){
		userDao.updateCid(user);
	}
	public void addUser(User user) {
		userDao.save(user);
	}
	
	public boolean checkUserPassword(User user){
		return userDao.checkUserPassword(user);
	}
	public boolean deleteChild(User user,String childName){
		return userDao.deleteChild(user,childName);
	}
	public void addChild(String username,String childName){
		userDao.addChild(username,childName);
	}
	public String getCidByUsername(String username){
		return userDao.getCidByUsername(username);
	}
}
