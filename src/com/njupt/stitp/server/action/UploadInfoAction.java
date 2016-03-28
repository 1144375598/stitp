package com.njupt.stitp.server.action;

import com.njupt.stitp.server.model.GeoFencing;
import com.njupt.stitp.server.model.UseTimeControl;
import com.njupt.stitp.server.service.InfoManager;
import com.njupt.stitp.server.service.UserManager;

public class UploadInfoAction {
	private UseTimeControl useTimeControl;
	private GeoFencing geoFencing;
	private String info;
	private InfoManager infoManager = new InfoManager();
	private UserManager userManager = new UserManager();

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public UseTimeControl getUseTimeControl() {
		return useTimeControl;
	}

	public void setUseTimeControl(UseTimeControl useTimeControl) {
		this.useTimeControl = useTimeControl;
	}

	public InfoManager getInfoManager() {
		return infoManager;
	}

	public void setInfoManager(InfoManager infoManager) {
		this.infoManager = infoManager;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void uploadAPPInfo() {
		infoManager.addAPPInfo(info);
	}

	public void uploadTrackInfo() {
		infoManager.addTrackInfo(info);
	}

	public void uploadUseTimeControlInfo() {
		infoManager.addUseTimeControlInfo(info);
	}

	public void uploadGenFencingInfo() {
		infoManager.addGenFencingInfo(geoFencing);
		System.out.println(geoFencing);
	}

	public GeoFencing getGeoFencing() {
		return geoFencing;
	}

	public void setGeoFencing(GeoFencing geoFencing) {
		this.geoFencing = geoFencing;
	}

}
