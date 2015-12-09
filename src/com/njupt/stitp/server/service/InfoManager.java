package com.njupt.stitp.server.service;

import java.util.Date;

import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.njupt.stitp.server.dao.InfoDao;
import com.njupt.stitp.server.dto.APPDto;
import com.njupt.stitp.server.dto.ContinueUseTimeDto;
import com.njupt.stitp.server.dto.GeoFencingDto;
import com.njupt.stitp.server.dto.TrackDto;
import com.njupt.stitp.server.dto.UseTimeControlDto;
import com.njupt.stitp.server.dto.ValidationQuestionDto;
import com.njupt.stitp.server.model.APP;
import com.njupt.stitp.server.model.GeoFencing;
import com.njupt.stitp.server.model.Track;
import com.njupt.stitp.server.model.UseTimeControl;
import com.njupt.stitp.server.model.User;
import com.njupt.stitp.server.model.ValidationQuestion;
import com.njupt.stitp.sever.util.CalDistance;

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

	public void addAPPInfo(APP app) {
		infoDao.saveAPPInfo(app);
	}

	public void addTrackInfo(Track track) {
		infoDao.saveTrackInfo(track);
	}

	public List<APPDto> getAPPInfo(User user, Date date) {
		List<APP> appsTemp = infoDao.getAPPInfo(user, date);
		List<APPDto> apps = new Vector<APPDto>();
		for (APP app : appsTemp) {
			APPDto appDto = new APPDto();
			appDto.setAppName(app.getAppName());
			appDto.setAppUseTime(app.getAppUseTime());
			appDto.setDate(app.getAddDate());
			appDto.setUsername(app.getUser().getUsername());
			apps.add(appDto);
		}
		return apps;
	}

	public List<TrackDto> getTrackInfo(User user, Date date) {
		List<Track> tracksTemp = infoDao.getTrackInfo(user, date);
		List<TrackDto> tracks = new Vector<TrackDto>();
		for (Track track : tracksTemp) {
			TrackDto trackDto = new TrackDto();
			trackDto.setLatitude(track.getLatitude());
			trackDto.setLongitude(track.getLongitude());
			trackDto.setUsername(track.getUser().getUsername());
			trackDto.setAddTime(track.getAddTime());
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

	public List<ValidationQuestionDto> getQuestionInfo(User user) {
		List<ValidationQuestion> vqs = infoDao.getVqInfo(user);
		List<ValidationQuestionDto> vqds = new Vector<ValidationQuestionDto>();
		for (ValidationQuestion vq : vqs) {
			ValidationQuestionDto vqd = new ValidationQuestionDto();
			vqd.setAnswer(vq.getAnswer());
			vqd.setQuestion(vq.getQuestion());
			vqd.setUsername(vq.getUser().getUsername());
			vqds.add(vqd);
		}
		return vqds;
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
