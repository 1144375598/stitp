package com.njupt.stitp.server.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.njupt.stitp.server.dto.UserDto;
import com.njupt.stitp.server.model.User;
import com.njupt.stitp.server.service.UserManager;
import com.njupt.stitp.sever.util.BaiduPush;
import com.njupt.stitp.sever.util.MD5Code;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private User user;
	private String friendName;
	private UserManager userManager = new UserManager();
	private MD5Code md5Code = new MD5Code();
	private String message;
	private String relationship;
	private Integer resultCode;

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
		System.out.println("*************************" + user.getUsername());
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
		/*
		 * result_code: 0 删除成功 1 不可删除家长
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		if (userManager.deleteChild(user, friendName)) {
			resultMap.put("result_code", 0);
		} else {
			resultMap.put("result_code", 1);
		}

		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void uidAndCid() {
		userManager.updateCid(user);
	}

	public void addFriend() {
		/*
		 * result_code 0 验证消息发送成功 1 消息发送失败
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");

		String mes = "username =" + user.getUsername() + "," + "message ="
				+ message + "," + "relationship =" + relationship;
		String result = BaiduPush.PushMsgToSingle(
				userManager.getCidByUsername(friendName), mes);

		if (result.equals("Success")) {
			resultMap.put("result_code", 0);
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
		String result = "friendName =" + friendName + "," + "relationship ="
				+ relationship + "," + "addFriendResult =";
		if (resultCode == 1)
			BaiduPush.PushMsgToSingle(
					userManager.getCidByUsername(user.getUsername()), result
							+ "1");
		else if (resultCode == 0) {
			if (relationship.equals("child"))
				userManager.addChild(user.getUsername(), friendName);
			else if (relationship.equals("parents")) {
				userManager.addChild(friendName, user.getUsername());
			}
			BaiduPush.PushMsgToSingle(
					userManager.getCidByUsername(user.getUsername()), result
							+ "0");
		}
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public void sendMessage() {
		/*
		 * result_code 0 消息发送成功 1 消息发送失败
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");

		String mes = "username =" + user.getUsername() + "," + "message ="
				+ message + "," + "date =" + new Date().toString();
		String result = BaiduPush.PushMsgToSingle(
				userManager.getCidByUsername(friendName), mes);

		if (result.equals("Success")) {
			resultMap.put("result_code", 0);
		} else {
			resultMap.put("result_code", 1);
		}

		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetPassword() {
		user.setPassword(md5Code.getMD5ofStr(user.getPassword()));
		userManager.updatePassword(user);
	}

	public void continueUseTime() {
		userManager.updateContinueTime(user);
		BaiduPush.PushMsgToSingle(userManager.getCidByUsername(user.getUsername()), "ContinueUseTime updated");
	}

	public void lockScreen() {
		BaiduPush.PushMsgToSingle(userManager.getCidByUsername(user.getUsername()),"lock screen");
	}
	public void getChild(){
		/*
		 * result_code:
		 * 0：有孩子
		 * 1:无孩子
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext
				.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		List<UserDto> childs = userManager.getChild(user.getUsername());
		if(childs.size()==0){
			resultMap.put("result_code", 1);
		}else{
			resultMap.put("result_code", 0);
			resultMap.put("result", childs);
		}
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
