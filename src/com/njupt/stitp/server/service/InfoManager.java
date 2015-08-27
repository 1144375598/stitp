package com.njupt.stitp.server.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.njupt.stitp.server.dao.InfoDao;
import com.njupt.stitp.server.model.APP;
import com.njupt.stitp.server.model.Track;

@Component
public class InfoManager {
	private InfoDao infoDao;

	public InfoDao getInfoDao() {
		return infoDao;
	}
	@Resource
	public void setInfoDao(InfoDao infoDao) {
		this.infoDao = infoDao;
	}
	public void addAPPInfo(APP app){
		infoDao.saveAPPInfo(app);
	}
	public void addTrackInfo(Track track){
		infoDao.saveTrackInfo(track);
	}
}
