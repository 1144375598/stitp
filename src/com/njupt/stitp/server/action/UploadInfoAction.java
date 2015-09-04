package com.njupt.stitp.server.action;



import java.util.Date;

import com.njupt.stitp.server.model.APP;
import com.njupt.stitp.server.model.GeoFencing;
import com.njupt.stitp.server.model.Track;
import com.njupt.stitp.server.model.UseTimeControl;
import com.njupt.stitp.server.service.InfoManager;

public class UploadInfoAction {
	private APP app;
	private Track track;
	private UseTimeControl useTimeControl;
	private GeoFencing geoFencing;
	private InfoManager infoManager=new InfoManager();
	
	public UseTimeControl getUseTimeControl() {
		return useTimeControl;
	}
	public void setUseTimeControl(UseTimeControl useTimeControl) {
		this.useTimeControl = useTimeControl;
	}
	
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
		track.setAddTime(new Date());
		infoManager.addTrackInfo(track);
	}
	public void uploadUseTimeControlInfo(){
		infoManager.addUseTimeControlInfo(useTimeControl);
	}
	public void uploadGenFencingInfo(){
		infoManager.addGenFencingInfo(geoFencing);
	}
	public GeoFencing getGeoFencing() {
		return geoFencing;
	}
	public void setGeoFencing(GeoFencing geoFencing) {
		this.geoFencing = geoFencing;
	}
}
