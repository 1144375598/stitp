package com.njupt.stitp.server.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.njupt.stitp.server.model.APP;
import com.njupt.stitp.server.model.GeoFencing;
import com.njupt.stitp.server.model.Track;
import com.njupt.stitp.server.model.UseTimeControl;
import com.njupt.stitp.server.model.User;
import com.njupt.stitp.server.model.ValidationQuestion;

@Component
public class InfoDao {
	SessionFactory sf;

	public SessionFactory getSf() {
		return sf;
	}

	@Resource(name = "sessionFactory")
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	public void saveTrackInfo(List<Track> tracks) {
		Session session = sf.getCurrentSession();
		for (Track track : tracks) {
			session.save(track);
		}
	}

	public void saveUseTimeControlInfo(UseTimeControl useTimeControl) {
		sf.getCurrentSession().save(useTimeControl);
	}

	public void saveAPPInfo(List<APP> apps) {
		Session session = sf.getCurrentSession();
		for (APP app : apps) {
			Query query = session
					.createQuery(
							"from APP app where app.user.username=:username and app.addDate=:addDate and app.appName=:appName")
					.setString("username", app.getUser().getUsername())
					.setDate("addDate", app.getAddDate())
					.setString("appName", app.getAppName());
			List<APP> apps2 = (List<APP>) query.list();
			if (apps2.size() == 0) {
				session.save(app);
			} else {
				apps2.get(0).setAppUseTime(app.getAppUseTime());
				session.update(apps2.get(0));
			}
		}
	}

	public List<APP> getAPPInfo(User user, Date date) {
		Session session = sf.getCurrentSession();
		Query query = session
				.createQuery(
						"from APP app where app.user.username=:username and app.addDate=:addDate")
				.setString("username", user.getUsername())
				.setDate("addDate", date);
		List<APP> apps = (List<APP>) query.list();

		return apps;
	}

	public List<Track> getTrackInfo(User user, Date dateStart) {
		Session session = sf.getCurrentSession();
		Date dateEnd = new Date(dateStart.getTime());
		dateEnd.setHours(23);
		dateEnd.setMinutes(59);
		dateEnd.setSeconds(59);
		dateStart.setHours(0);
		dateStart.setMinutes(0);
		dateStart.setSeconds(0);
		Query query = session
				.createQuery(
						"from Track track where track.user.username = :username "
								+ "and track.addTime >= :dateStart and track.addTime < :dateEnd")
				.setString("username", user.getUsername())
				.setTimestamp("dateStart", dateStart)
				.setTimestamp("dateEnd", dateEnd);
		List<Track> tracks = (List<Track>) query.list();
		return tracks;
	}

	public List<UseTimeControl> getUseTimeControlInfo(User user) {
		Session session = sf.getCurrentSession();
		Query query = session.createQuery(
				"from UseTimeControl u where u.user.username = :username")
				.setString("username", user.getUsername());
		List<UseTimeControl> useTimeControls = (List<UseTimeControl>) query
				.list();
		return useTimeControls;
	}

	public void saveGeoFencingInfo(GeoFencing geoFencing) {
		Session session = sf.getCurrentSession();
		Query query = session.createQuery(
				"from GeoFencing g where g.user.username = :username")
				.setString("username", geoFencing.getUser().getUsername());
		List<GeoFencing> geoFencings = query.list();
		if (geoFencings.size() == 0) {
			session.save(geoFencing);
		} else {
			geoFencings.get(0).setDistance(geoFencing.getDistance());
			geoFencings.get(0).setLatitude(geoFencing.getLatitude());
			geoFencings.get(0).setLongtitude(geoFencing.getLongtitude());
			session.update(geoFencings.get(0));
		}
	}

	public List<GeoFencing> getGeoFencingInfo(User user) {
		Session session = sf.getCurrentSession();
		Query query = session.createQuery(
				"from GeoFencing g where g.user.username = :username")
				.setString("username", user.getUsername());
		List<GeoFencing> geoFencings = (List<GeoFencing>) query.list();
		return geoFencings;
	}

	public List<ValidationQuestion> getVqInfo(User user) {
		Session session = sf.getCurrentSession();
		Query query = session.createQuery(
				"from ValidationQuestion vq where vq.user.username=:username")
				.setString("username", user.getUsername());
		List<ValidationQuestion> vqs = query.list();
		return vqs;
	}

	public boolean getFlag(User user) {
		Session session = sf.getCurrentSession();
		User u = (User) session
				.createQuery("from User u where u.username = :username")
				.setString("username", user.getUsername()).uniqueResult();
		if (u.isFlag()) {
			return true;
		} else {
			return false;
		}
	}

	public void setFlag(User user, boolean f) {
		Session session = sf.getCurrentSession();
		User u = (User) session
				.createQuery("from User u where u.username = :username")
				.setString("username", user.getUsername()).uniqueResult();
		u.setFlag(f);
		session.update(u);
	}

	public User getContinueUseTime(User user) {
		Session session = sf.getCurrentSession();
		User u = (User) session
				.createQuery("from User u where u.username = :username")
				.setString("username", user.getUsername()).uniqueResult();
		return u;
	}
}
