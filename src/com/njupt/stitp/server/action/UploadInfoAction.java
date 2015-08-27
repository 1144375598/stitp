package com.njupt.stitp.server.action;



import com.njupt.stitp.server.model.APP;
import com.njupt.stitp.server.model.Track;
import com.njupt.stitp.server.service.InfoManager;

public class UploadInfoAction {
	private APP app;
	private Track track;
	private InfoManager infoManager;
	
	public APP getApp() {
		return app;
	}
	public void setApp(APP app) {
		this.app = app;
	}
	public Track getTrack() {
		return track;
	}
	public void setTrack(Track track) {
		this.track = track;
	}
	public InfoManager getInfoManager() {
		return infoManager;
	}
	public void setInfoManager(InfoManager infoManager) {
		this.infoManager = infoManager;
	}
	
	public void uploadAPPInfo(){
		app.setAddDate(new java.sql.Date(new java.util.Date().getTime()));
		infoManager.addAPPInfo(app);
	}
	public void uploadTrackInfo(){
		track.setAddTime(new java.util.Date());
		infoManager.addTrackInfo(track);
	}
	
}
