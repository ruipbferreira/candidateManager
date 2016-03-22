package com.interviewmanagement.main.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.interviewmanagement.main.model.RefData;

@Repository
public class RefDataDAOImpl implements RefDataDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RefData> listRefData(String field) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "from RefData rd where rd.field = :field";
		List<RefData> refDataList = session.createQuery(hql).setParameter("field", field).list();
		return refDataList;
	}
}