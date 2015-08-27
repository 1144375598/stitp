package com.njupt.stitp.server.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.njupt.stitp.server.model.APP;
import com.njupt.stitp.server.model.Track;
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
}
