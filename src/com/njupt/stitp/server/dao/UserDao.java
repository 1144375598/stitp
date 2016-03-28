package com.njupt.stitp.server.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.njupt.stitp.server.model.User;

@Component("userDao")
public class UserDao {

	SessionFactory sf;

	public void save(User user) {
		sf.getCurrentSession().save(user);
	}

	public SessionFactory getSf() {
		return sf;
	}

	@Resource(name = "sessionFactory")
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	public boolean checkUserPassword(User user) {
		Session s = sf.getCurrentSession();
		long count = (Long) s
				.createQuery(
						"select count(*) From User u where u.username =:username and u.password=:password")
				.setString("username", user.getUsername())
				.setString("password", user.getPassword()).uniqueResult();
		if (count == 1)
			return true;
		return false;
	}

	public boolean checkUserExistsWithName(User user) {
		Session s = sf.getCurrentSession();
		long count = (Long) s
				.createQuery(
						"select count(*) from User u where u.username =:username")
				.setString("username", user.getUsername()).uniqueResult();
		if (count > 0) {
			return true;
		}
		return false;
	}

	public boolean deleteChild(User user, String childName) {
		Session s = sf.getCurrentSession();
		User u = (User) s.createQuery("from User u where u.username=:username")
				.setString("username", user.getUsername()).uniqueResult();
		for (User child : u.getChildren()) {
			if (child.getUsername().equals(childName))
				u.getChildren().remove(child);
			s.update(u);
			return true;
		}
		return false;
	}

	public void updateCid(User user) {
		Session s = sf.getCurrentSession();
		Query query = s
				.createQuery(
						"update User u set u.cid=:cid where u.username=:username")
				.setString("cid", user.getCid())
				.setString("username", user.getUsername());
		query.executeUpdate();
	}

	public String getCidByUsername(String username) {
		Session s = sf.getCurrentSession();
		User u = (User) s.createQuery("from User u where u.username=:username")
				.setString("username", username).uniqueResult();
		return u.getCid();
	}

	public void addChild(String username, String childName) {
		Session s = sf.getCurrentSession();
		User u = (User) s.createQuery("from User u where u.username=:username")
				.setString("username", username).uniqueResult();
		User child = (User) s
				.createQuery("from User u where u.username=:username")
				.setString("username", childName).uniqueResult();
		u.getChildren().add(child);
		s.update(u);
	}

	public void updatePassword(User user) {
		Session s = sf.getCurrentSession();
		Query query = s
				.createQuery(
						"update User u set u.password=:password where u.username=:username")
				.setString("password", user.getPassword())
				.setString("username", user.getUsername());
		query.executeUpdate();
	}

	public List<User> getParents(User user) {
		Session s = sf.getCurrentSession();
		SQLQuery query = ((SQLQuery) s
				.createSQLQuery(
						"select * from user where username in (select parent_id from relationship where child_id = :username) ")
				.setString("username", user.getUsername()))
				.addEntity(User.class);
		List<User> users = query.list();
		return users;
	}

	public void updateContinueTime(User user) {
		Session s = sf.getCurrentSession();
		Query query = s
				.createQuery(
						"update User u set u.timeOfContinuousUse=:useTime where u.username=:username")
				.setString("useTime",
						((Integer) user.getTimeOfContinuousUse()).toString())
				.setString("username", user.getUsername());
		query.executeUpdate();
	}

	public List<User> getChild(String parentsName) {
		Session s = sf.getCurrentSession();
		SQLQuery query = ((SQLQuery) s
				.createSQLQuery(
						"select * from user where username in (select child_id from relationship where parent_id = :username) ")
				.setString("username", parentsName)).addEntity(User.class);
		List<User> childs = query.list();
		return childs;
	}

	public User getUser(String username) {
		Session s = sf.getCurrentSession();
		User u = (User) s.createQuery("from User u where u.username=:username")
				.setString("username", username).uniqueResult();
		return u;
	}

	public void updateLockPwd(String lockPwd, String username) {
		Session s = sf.getCurrentSession();
		Query query = s
				.createQuery(
						"update User u set u.lockPwd=:lockPwd where u.username=:username")
				.setString("lockPwd", lockPwd).setString("username", username);
		query.executeUpdate();
	}
}
