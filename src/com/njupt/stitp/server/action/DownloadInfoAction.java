package com.njupt.stitp.server.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.njupt.stitp.server.dto.APPDto;
import com.njupt.stitp.server.dto.TrackDto;
import com.njupt.stitp.server.dto.UseTimeControlDto;
import com.njupt.stitp.server.model.User;
import com.njupt.stitp.server.service.InfoManager;

public class DownloadInfoAction {
	private User user;
	private String dateString;
	private InfoManager infoManager = new InfoManager();


	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public InfoManager getInfoManager() {
		return infoManager;
	}

	public void setInfoManager(InfoManager infoManager) {
		this.infoManager = infoManager;
	}

	
	
	public void downloadAPPInfo() {		
		/*
		 * result_code
		 * 0 查询成功
		 * 1 参数错误
		 * 2 无app信息
		 */
		HttpServletResponse servletResponse = ServletActionContext.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		Map<Integer, Object> resultMap=new HashMap<Integer, Object>();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		Date date=null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e1) {
			resultMap.put(1, "wrong parameters");
			try {
				servletResponse.getWriter().write(new Gson().toJson(resultMap));
			} catch (IOException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
			return ;
		}	
		List<APPDto> apps = infoManager.getAPPInfo(user,date);
		if(apps.size()==0){
			resultMap.put(2, "no result");
		} else{
			resultMap.put(0, apps);	
		}			
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void downloadTrackInfo() {
		/*
		 * result_code
		 * 0 查询成功
		 * 1 参数错误
		 * 2 无轨迹信息
		 */
		HttpServletResponse servletResponse = ServletActionContext.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		Map<Integer, Object> resultMap=new HashMap<Integer, Object>();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		Date date=null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e1) {
			resultMap.put(1, "wrong parameters");
			try {
				servletResponse.getWriter().write(new Gson().toJson(resultMap));
			} catch (IOException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
			return ;
		}	
		List<TrackDto> tracks = infoManager.getTrackInfo(user,date);
		if(tracks.size()==0){
			resultMap.put(2, "no result");
		} else{
			resultMap.put(0, tracks);	
		}			
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void downloadTimeControlInfo(){
		/*
		 * result_code
		 * 0 查询成功
		 * 1 无使用时间控制信息
		 */
		HttpServletResponse servletResponse = ServletActionContext.getResponse();
		servletResponse.setContentType("text/html;charset=utf-8");
		servletResponse.setCharacterEncoding("UTF-8");
		Map<Integer, Object> resultMap=new HashMap<Integer, Object>();
		List<UseTimeControlDto> useTimeControlDtos = infoManager.getUseTimeControlInfo(user);
		if(useTimeControlDtos.size()==0){
			resultMap.put(1, "no result");
		} else{
			resultMap.put(0, useTimeControlDtos);	
		}			
		try {
			servletResponse.getWriter().write(new Gson().toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
