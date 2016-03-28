package com.njupt.stitp.server.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.njupt.stitp.server.dto.UserDto;
import com.njupt.stitp.server.model.User;
import com.njupt.stitp.server.service.UserManager;
import com.njupt.stitp.server.util.BaiduPush;
import com.njupt.stitp.server.util.MD5Code;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private User user;
	private String friendName;
	private UserManager userManager = new UserManager();
	private MD5Code md5Code = new MD5Code();
	private String relationship;
	private Integer resultCode;
	private Integer serviceCode;

	public Integer getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(Integer serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MD5Code getMd5Code() {
		return md5Code;
	}

	public void setMd5Code(MD5Code md5Code) {
		this.md5Code = md5Code;
	}

	public void login() {
		/*
		 * result_code: 0: 登陆成功！ 1：登陆失败，用户名或密码错误！ 2：登陆失败，用户名不存在！
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		user.setPassword(md5Code.getMD5ofStr(user.getPassword()));
		if (!userManager.exists(user)) {
			resultMap.put("result_code", 2);
		} else if (!userManager.checkUserPassword(user)) {
			resultMap.put("result_code", 1);
		} else if (userManager.checkUserPassword(user)) {
			resultMap.put("result_code", 0);
		}
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void register() {
		/*
		 * result_code: 0 注册成功 1 用户名已存在
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		user.setPassword(md5Code.getMD5ofStr(user.getPassword()));
		if (userManager.exists(user)) {
			resultMap.put("result_code", 1);
		} else {
			userManager.addUser(user);
			resultMap.put("result_code", 0);
		}

		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteChild() {
		userManager.deleteChild(user, friendName);
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", friendName);
		params.put("parentName", user.getUsername());
		params.put("serviceCode", "13");
		BaiduPush.pushMsgToSingle(userManager.getCidByUsername(friendName),
				params, 7 * 24 * 3600);
	}

	public void uidAndCid() {
		System.out.println("************************" + user.getUsername()
				+ "***" + user.getCid());
		userManager.updateCid(user);
	}

	public void addFriend() {
		/*
		 * result_code 0 验证消息发送成功 1 无该用户
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		User user2 = new User();
		user2.setUsername(friendName);
		if (userManager.exists(user2)) {
			resultMap.put("result_code", 0);
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", friendName);
			params.put("relationship", relationship);
			params.put("requestName", user.getUsername());
			params.put("serviceCode", "11");
			BaiduPush.pushMsgToSingle(userManager.getCidByUsername(friendName),
					params, 7 * 24 * 3600);
		} else {
			resultMap.put("result_code", 1);
		}

		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addFriendResult() {
		Map<String, String> params = new HashMap<String, String>();
		if (resultCode == 0) {
			if (relationship.equals("child"))
				userManager.addChild(user.getUsername(), friendName);
			else if (relationship.equals("parents")) {
				userManager.addChild(friendName, user.getUsername());
			}
		}
		params.put("username", user.getUsername());
		params.put("friendName", friendName);
		params.put("resultCode", resultCode.toString());
		params.put("relationship", relationship);
		params.put("serviceCode", "12");
		BaiduPush.pushMsgToSingle(
				userManager.getCidByUsername(user.getUsername()), params,
				7 * 24 * 3600);
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	

	public void resetPassword() {
		user.setPassword(md5Code.getMD5ofStr(user.getPassword()));
		userManager.updatePassword(user);
	}

	/*
	 * 打开定时护眼 7 关闭定时护眼 8 定时护眼时间更改 9
	 */
	public void continueUseTime() {
		Map<String, String> params = new HashMap<String, String>();
		if (serviceCode == 8) {
			params.put("username", user.getUsername());
			params.put("serviceCode", "8");
			BaiduPush.pushMsgToSingle(
					userManager.getCidByUsername(user.getUsername()), params,
					12 * 3600);
		} else if (serviceCode == 9) {
			userManager.updateContinueTime(user);
			params.put("username", user.getUsername());
			params.put("serviceCode", "9");
			params.put("continueUseTime",
					((Integer) (user.getTimeOfContinuousUse())).toString());
			BaiduPush.pushMsgToSingle(
					userManager.getCidByUsername(user.getUsername()), params,
					24 * 3600);
		} else if (serviceCode == 7) {
			params.put("username", user.getUsername());
			params.put("serviceCode", "7");
			BaiduPush.pushMsgToSingle(
					userManager.getCidByUsername(user.getUsername()), params,
					12 * 3600);
		} else if (serviceCode == 0) {
			params.put("username", user.getUsername());
			params.put("serviceCode", "0");
			params.put("lockPwd", user.getLockPwd());
			BaiduPush.pushMsgToSingle(
					userManager.getCidByUsername(user.getUsername()), params,
					12 * 3600);
		}
	}

	public void bumpRemind() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user.getUsername());
		params.put("serviceCode", serviceCode.toString());
		BaiduPush.pushMsgToSingle(
				userManager.getCidByUsername(user.getUsername()), params,12*3600);
	}

	public void lockScreen() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user.getUsername());
		params.put("serviceCode", "1");
		params.put("lockPwd", user.getLockPwd());

		BaiduPush.pushMsgToSingle(
				userManager.getCidByUsername(user.getUsername()), params,5*60);
		userManager.updateLockPwd(user.getLockPwd(), user.getUsername());
	}

	public void unlockScreen() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", user.getUsername());
		params.put("serviceCode", "2");
		BaiduPush.pushMsgToSingle(
				userManager.getCidByUsername(user.getUsername()), params,5*60);
	}

	public void getChild() {
		/*
		 * result_code: 0：有孩子 1:无孩子
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		List<UserDto> childs = userManager.getChild(user.getUsername());
		if (childs.size() == 0) {
			resultMap.put("result_code", 1);
		} else {
			resultMap.put("result_code", 0);
			resultMap.put("result", childs);
		}
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getInfo() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		UserDto userDto = userManager.getUser(user.getUsername());
		if (userDto == null) {
			resultMap.put("result_code", 1);
		} else {
			resultMap.put("result_code", 0);
			resultMap.put("result", userDto);
		}
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outOfRange() {
		List<User> parents = userManager.getParents(user);
		for (User parent : parents) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", parent.getUsername());
			params.put("serviceCode", "14");
			params.put("childName", user.getUsername());
			BaiduPush.pushMsgToSingle(
					userManager.getCidByUsername(parent.getUsername()), params,
					3600 * 5);
		}
	}

	public void getValidation() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		if (!userManager.exists(user)) {
			resultMap.put("result_code", 1);
		} else {
			Map<String, String> validation = userManager.getValidation(user
					.getUsername());
			resultMap.put("result_code", 0);
			resultMap.put("question", validation.get("question"));
			resultMap.put("answer", validation.get("answer"));
		}
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
