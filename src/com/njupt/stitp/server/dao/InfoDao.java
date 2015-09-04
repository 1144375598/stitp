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
@Component
public class InfoDao {
	SessionFactory sf;

	public SessionFactory getSf() {
		return sf;
	}
	@Resource(name="sessionFactory")
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	public void saveTrackInfo(Track track){
		sf.getCurrentSession().save(track);
	}
	public void saveUseTimeControlInfo(UseTimeControl useTimeControl){
		sf.getCurrentSession().save(useTimeControl);
	}
	public void saveAPPInfo(APP app){
		Session session=sf.getCurrentSession();	
		Query query=session.createQuery("from APP app where app.user.username=:username and app.addDate=:addDate and app.appName=:appName")
				.setString("username", app.getUser().getUsername())
				.setDate("addDate", app.getAddDate())
				.setString("appName", app.getAppName());
		List<APP> apps =(List<APP>)query.list();
		if(apps.size()==0){
			session.save(app);
		}
		else {
			apps.get(0).setAppUseTime(app.getAppUseTime());
			session.update(apps.get(0));
		}		
	}
	public List<APP> getAPPInfo(User user,Date date){
		Session session=sf.getCurrentSession();	
		Query query=session.createQuery("from APP app where app.user.username=:username and app.addDate=:addDate")
				.setString("username", user.getUsername())
				.setDate("addDate", date);
		List<APP> apps =(List<APP>)query.list();
		
		return apps;
	}
	public List<Track> getTrackInfo(User user,Date dateStart){
		Session session=sf.getCurrentSession();	
		Date dateEnd= new Date(dateStart.getTime());
		dateEnd.setHours(23);
		dateEnd.setMinutes(59);
		dateEnd.setSeconds(59);
		dateStart.setHours(0);
		dateStart.setMinutes(0);
		dateStart.setSeconds(0);
		Query query=session.createQuery("from Track track where track.user.username = :username "
				+ "and track.addTime >= :dateStart and track.addTime < :dateEnd")
				.setString("username", user.getUsername())
				.setTimestamp("dateStart", dateStart)
				.setTimestamp("dateEnd", dateEnd);
		List<Track> tracks=(List<Track>)query.list();
		return tracks;
	}
	
	
	public List<UseTimeControl> getUseTimeControlInfo(User user){
		Session session =sf.getCurrentSession();
		Query query = session.createQuery("from UseTimeControl u where u.user.username = :username")
				.setString("username", user.getUsername());
		List<UseTimeControl> useTimeControls =(List<UseTimeControl>)query.list();
		return useTimeControls;
	}
	public void saveGeoFencingInfo(GeoFencing geoFencing){
		sf.getCurrentSession().save(geoFencing);
	}
}
