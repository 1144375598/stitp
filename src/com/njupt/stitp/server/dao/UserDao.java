package com.njupt.stitp.server.dao;

import javax.annotation.Resource;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.njupt.stitp.server.model.User;

@Component("userDao")
public class UserDao {
	
	SessionFactory sf;
	

	public void save(User user){
		sf.getCurrentSession().save(user);
	}

	public SessionFactory getSf() {
		return sf;
	}
	
	@Resource(name="sessionFactory")
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	public boolean checkUserPassword(User user){
		Session s=sf.getCurrentSession();
		long count=(Long)s.createQuery("select count(*) From User u where u.name =:username and u.password=:password")
				.setString("username", user.getUsername())
				.setString("password", user.getPassword())
				.uniqueResult();
		if (count==1) return true;
		return false;	
	}
	
	public boolean checkUserExistsWithName(User user) {
		Session s=sf.getCurrentSession();
		long count=(Long)s.createQuery("select count(*) from User u where u.username =:username")
				.setString("username", user.getUsername())
				.uniqueResult();
		if (count>0) {
			return true;
		}	
		return false;	
	}
	
	
}
