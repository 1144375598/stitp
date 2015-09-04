package com.njupt.stitp.server.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.njupt.stitp.server.model.User;
import com.njupt.stitp.server.service.UserManager;
import com.njupt.stitp.sever.util.MD5Code;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport  {

	private static final long serialVersionUID = 1L;

	private User user;
	private String friendName;
	private UserManager userManager=new UserManager();
	private MD5Code md5Code=new MD5Code();
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
		 * result_code:
		 * 0: 登陆成功！
		 * 1：登陆失败，用户名或密码错误！
		 * 2：登陆失败，用户名不存在！
		 * */
		Map<String, Object> resultMap=new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		user.setPassword(md5Code.getMD5ofStr(user.getPassword()));
		if(!userManager.exists(user)){
			resultMap.put("result_code", 2);
		} else if(!userManager.checkUserPassword(user)){
			resultMap.put("result_code", 1);
		} else if(userManager.checkUserPassword(user)){
			resultMap.put("result_code", 0);
		}
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	public void register(){
		/*
		 *   result_code: 
		 * 0 注册成功
		 * 1  用户名已存在
		 * */
		Map<String, Object> resultMap=new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		user.setPassword(md5Code.getMD5ofStr(user.getPassword()));
		if(userManager.exists(user)){
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
	public void deleteFriend(){
		/*
		 *   result_code: 
		 * 0 删除成功
		 * 1  不可删除家长
		 * */
		Map<String, Object> resultMap=new HashMap<String, Object>();
		HttpServletResponse servletResponse = ServletActionContext.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		if(userManager.deleteFriend(user,friendName)){
			resultMap.put("result_code", 0);
		} else{
			resultMap.put("result_code", 1);
		}
		
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	
	

}
