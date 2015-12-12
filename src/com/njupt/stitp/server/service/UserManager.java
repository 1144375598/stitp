package com.njupt.stitp.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.njupt.stitp.server.dao.UserDao;
import com.njupt.stitp.server.dto.UserDto;
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

	public boolean exists(User user) {
		if (userDao.checkUserExistsWithName(user)) {
			return true;
		}
		return false;
	}

	public void updateCid(User user) {
		userDao.updateCid(user);
	}

	public void addUser(User user) {
		userDao.save(user);
	}

	public boolean checkUserPassword(User user) {
		return userDao.checkUserPassword(user);
	}

	public boolean deleteChild(User user, String childName) {
		return userDao.deleteChild(user, childName);
	}

	public void addChild(String username, String childName) {
		userDao.addChild(username, childName);
	}

	public String getCidByUsername(String username) {
		return userDao.getCidByUsername(username);
	}

	public void updatePassword(User user) {
		userDao.updatePassword(user);
	}

	public List<User> getParents(User user) {
		return userDao.getParents(user);
	}

	public void updateContinueTime(User user) {
		userDao.updateContinueTime(user);
	}

	public List<UserDto> getChild(String parentsName) {
		List<User> childsTemp = userDao.getChild(parentsName);
		List<UserDto> childs = new ArrayList<UserDto>();
		for (User user : childsTemp) {
			UserDto userDto = new UserDto();
			userDto.setCid(user.getCid() == null ? "0" : user.getCid());
			userDto.setMusicVolume(user.getMusicVolume());
			userDto.setTimeOfContinuousListen(user.getTimeOfContinuousListen());
			userDto.setTimeOfContinuousUse(user.getTimeOfContinuousUse());
			userDto.setUsername(user.getUsername());
			childs.add(userDto);
		}
		return childs;
	}
}
