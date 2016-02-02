package com.njupt.stitp.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.njupt.stitp.server.dao.InfoDao;
import com.njupt.stitp.server.dto.APPDto;
import com.njupt.stitp.server.dto.ContinueUseTimeDto;
import com.njupt.stitp.server.dto.GeoFencingDto;
import com.njupt.stitp.server.dto.TrackDto;
import com.njupt.stitp.server.dto.UseTimeControlDto;
import com.njupt.stitp.server.model.APP;
import com.njupt.stitp.server.model.GeoFencing;
import com.njupt.stitp.server.model.Track;
import com.njupt.stitp.server.model.UseTimeControl;
import com.njupt.stitp.server.model.User;
import com.njupt.stitp.server.util.BaiduPush;
import com.njupt.stitp.server.util.CalDistance;

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

	public void addAPPInfo(String appInfo) {
		BASE64Decoder decoder = new BASE64Decoder();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Gson gson = new Gson();
		List<APPDto> apps = gson.fromJson(appInfo,
				new TypeToken<List<APPDto>>() {
				}.getType());
		List<APP> apps2 = new ArrayList<APP>();
		for (APPDto appDto : apps) {
			APP app = new APP();
			try {
				app.setAddDate(new java.sql.Date(format.parse(
						appDto.getAddDate()).getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			app.setAppName(appDto.getAppName());
			app.setAppUseTime(appDto.getAppUseTime());
			app.getUser().setUsername(appDto.getUsername());
			app.setIcon(appDto.getIcon());
			apps2.add(app);
		}
		infoDao.saveAPPInfo(apps2);
	}

	public void addTrackInfo(String trackInfo) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = new Gson();
		List<TrackDto> tracks = gson.fromJson(trackInfo,
				new TypeToken<List<TrackDto>>() {
				}.getType());
		List<Track> tracks2 = new ArrayList<Track>();
		for (TrackDto trackDto : tracks) {
			Track track = new Track();
			try {
				track.setAddTime(format.parse(trackDto.getAddTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			track.setLatitude(trackDto.getLatitude());
			track.setLongitude(trackDto.getLongitude());
			track.setAddress(trackDto.getAddress());
			track.setStayTime(trackDto.getStayTime());
			track.getUser().setUsername(trackDto.getUsername());
			if (outOfRange(track)) {
				UserManager userManager = new UserManager();
				List<User> users = userManager.getParents(track.getUser());
				for (User user : users) {
					String mes = track.getUser().getUsername()
							+ ", out of range";
					BaiduPush.PushMsgToSingle(
							userManager.getCidByUsername(user.getUsername()),
							mes);
				}
			}
			tracks2.add(track);
		}
		infoDao.saveTrackInfo(tracks2);
	}

	public List<APPDto> getAPPInfo(User user, Date date) {
		BASE64Encoder encoder = new BASE64Encoder();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<APP> appsTemp = infoDao.getAPPInfo(user, date);
		List<APPDto> apps = new ArrayList<APPDto>();
		for (APP app : appsTemp) {
			APPDto appDto = new APPDto();
			appDto.setAppName(app.getAppName());
			appDto.setAppUseTime(app.getAppUseTime());
			appDto.setAddDate(format.format(app.getAddDate()));
			appDto.setUsername(app.getUser().getUsername());
			appDto.setIcon(app.getIcon());
			apps.add(appDto);
		}
		return apps;
	}

	public List<TrackDto> getTrackInfo(User user, Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Track> tracksTemp = infoDao.getTrackInfo(user, date);
		List<TrackDto> tracks = new Vector<TrackDto>();
		for (Track track : tracksTemp) {
			TrackDto trackDto = new TrackDto();
			trackDto.setLatitude(track.getLatitude());
			trackDto.setLongitude(track.getLongitude());
			trackDto.setUsername(track.getUser().getUsername());
			trackDto.setAddTime(format.format(track.getAddTime()));
			trackDto.setAddress(track.getAddress());
			trackDto.setStayTime(track.getStayTime());
			tracks.add(trackDto);
		}
		return tracks;
	}

	public void addUseTimeControlInfo(UseTimeControl useTimeControl) {
		infoDao.saveUseTimeControlInfo(useTimeControl);
	}

	public List<UseTimeControlDto> getUseTimeControlInfo(User user) {
		List<UseTimeControl> useTimeControlsTemp = infoDao
				.getUseTimeControlInfo(user);
		List<UseTimeControlDto> useTimeControls = new Vector<UseTimeControlDto>();
		for (UseTimeControl useTimeControl : useTimeControlsTemp) {
			UseTimeControlDto useTimeControlDto = new UseTimeControlDto();
			useTimeControlDto.setEnd(useTimeControl.getEnd());
			useTimeControlDto.setStart(useTimeControl.getStart());
			useTimeControlDto.setUsername(useTimeControl.getUser()
					.getUsername());
			useTimeControls.add(useTimeControlDto);
		}
		return useTimeControls;
	}

	public void addGenFencingInfo(GeoFencing geoFencing) {
		infoDao.saveGeoFencingInfo(geoFencing);
	}

	public GeoFencingDto getGeoFencingInfo(User user) {
		GeoFencingDto geoFencingDto = new GeoFencingDto();
		List<GeoFencing> geoFencing = infoDao.getGeoFencingInfo(user);
		if (geoFencing.size() == 0) {
			geoFencingDto.setUsername("");
			;
			return geoFencingDto;
		} else {
			geoFencingDto.setDistance(geoFencing.get(0).getDistance());
			geoFencingDto.setLatitude(geoFencing.get(0).getLatitude());
			geoFencingDto.setLongtitude(geoFencing.get(0).getLongtitude());
			geoFencingDto.setUsername(user.getUsername());
			return geoFencingDto;
		}
	}

	public boolean outOfRange(Track track) {
		double distance;
		List<GeoFencing> gfs = infoDao.getGeoFencingInfo(track.getUser());
		for (GeoFencing gf : gfs) {
			distance = CalDistance.calDistance(track.getLatitude(),
					track.getLongitude(), gf.getLatitude(), gf.getLongtitude());
			if (distance > gf.getDistance()) {
				// getFlag为true表示超出范围的信息在之前已经发送给家长
				if (infoDao.getFlag(track.getUser()))
					return false;
				else {
					infoDao.setFlag(track.getUser(), true);
					return true;
				}
			}
		}
		infoDao.setFlag(track.getUser(), false);
		return false;
	}

	public ContinueUseTimeDto getContinueUseTime(User user) {
		User u = infoDao.getContinueUseTime(user);
		ContinueUseTimeDto cutd = new ContinueUseTimeDto();
		cutd.setContinueUseTime(u.getTimeOfContinuousUse());
		cutd.setUsername(u.getUsername());
		return cutd;
	}
}
