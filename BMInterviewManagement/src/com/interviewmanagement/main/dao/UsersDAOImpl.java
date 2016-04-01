package com.interviewmanagement.main.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interviewmanagement.main.model.User;

@Repository
public class UsersDAOImpl implements UsersDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> usersList = session.createQuery("from User").list();
		return usersList;
	}

	@Override
	public User getUserByUsername(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		
		criteria.add(Restrictions.eq("username", user.getUsername()));
		return (User) criteria.list().get(0);
	}

	@Override
	public User getUserById(int id) {
		Session session = this.sessionFactory.getCurrentSession();      
		User user = (User) session.load(User.class, new Integer(id));
		return user;
	}

	@Override
	public Integer createOrUpdateUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		if(user.getUserId() == null) {
			user.setRole("ROLE_USER");
//			user.setPassword(user.getUsername());
			session.persist(user);
			session.flush();
		} else {
			session.update(user);
		}
		return user.getUserId();
	}

	@Override
	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.load(User.class, new Integer(id));
		if(user.getEnabled()) {
			user.setEnabled(false);
		} else {
			user.setEnabled(true);
		}
		session.persist(user);
		session.flush();
	}
}