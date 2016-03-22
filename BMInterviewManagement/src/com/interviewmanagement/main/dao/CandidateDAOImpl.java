package com.interviewmanagement.main.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interviewmanagement.main.model.Candidate;
import com.interviewmanagement.main.model.Language;
import com.interviewmanagement.main.model.Locality;

@Repository
public class CandidateDAOImpl implements CandidateDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Candidate> listCandidate(Candidate candidate) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Candidate.class);

		if (candidate != null) {
			// ID
			if (candidate.getId() != null) {
				criteria.add(Restrictions.eq(Candidate.FIELD_ID, candidate.getId()));
			}
			// NAME
			if (candidate.getName() != null && candidate.getName() != "") {
				criteria.add(Restrictions.ilike(Candidate.FIELD_NAME, candidate.getName()));
			}
			// PHONE
			if (candidate.getPhone() != null && candidate.getPhone() != "") {
				criteria.add(Restrictions.eq(Candidate.FIELD_PHONE, candidate.getPhone()));
			}
			// EMAIL
			if (candidate.getEmail() != null && candidate.getEmail() != "") {
				criteria.add(Restrictions.eq(Candidate.FIELD_EMAIL, candidate.getEmail()));
			}
			// BIRTHDATE
			if (candidate.getBirthDate() != null) {
				criteria.add(Restrictions.eq(Candidate.FIELD_BIRTHDATE, candidate.getBirthDate()));
			}
			// LEVEL
			if (candidate.getLevel() != null && candidate.getLevel() != "" && !candidate.getLevel().equals("-1")) {
				criteria.add(Restrictions.eq(Candidate.FIELD_LEVEL, candidate.getLevel()));
			}
			// LOCAL
			if (candidate.getLocal() != null && candidate.getLocal().getLocalId() != null && candidate.getLocal().getLocalId() != -1) {
				criteria.add(Restrictions.eq(Candidate.FIELD_LOCAL, candidate.getLocal()));
			}
			// LANGUAGE
			if (candidate.getLanguage() != null && candidate.getLanguage().getLanguageId() != null && candidate.getLanguage().getLanguageId() != -1) {
				criteria.add(Restrictions.eq(Candidate.FIELD_LANGUAGE, candidate.getLanguage()));
			}
			//USER
			if (candidate.getUser() != null && candidate.getUser().getUserId() != null && candidate.getUser().getUserId() != -1) {
				criteria.add(Restrictions.eq(Candidate.FIELD_USER, candidate.getUser()));
			}
			if(candidate.getProfessionalInfo() != null) {
				if((candidate.getProfessionalInfo().getTecnology() != null && candidate.getProfessionalInfo().getTecnology() != "") ||
						(candidate.getProfessionalInfo().getArqDev() != null && candidate.getProfessionalInfo().getArqDev() != "") ||
						(candidate.getProfessionalInfo().getFunctional() != null && candidate.getProfessionalInfo().getFunctional() != "") ||
						candidate.getProfessionalInfo().getYear() != null) {
					criteria.createAlias(Candidate.FIELD_INFO, "info_alias");
				}
				
				// YEAR
				if(candidate.getProfessionalInfo().getYear() != null) {
					criteria.add(Restrictions.disjunction().add(Restrictions.eq("info_alias.year", candidate.getProfessionalInfo().getYear())));
				}
				// TECNOLOGIES
				if(candidate.getProfessionalInfo().getTecnology() != null && candidate.getProfessionalInfo().getTecnology() != "") {
					criteria.add(Restrictions.disjunction().add(Restrictions.ilike("info_alias.tecnology", "%" + candidate.getProfessionalInfo().getTecnology() + "%")));
				}
				// ARQUITECTURE
				if(candidate.getProfessionalInfo().getArqDev() != null && candidate.getProfessionalInfo().getArqDev() != "") {
					criteria.add(Restrictions.disjunction().add(Restrictions.ilike("info_alias.arqDev", "%" + candidate.getProfessionalInfo().getArqDev() + "%")));
				}
				// FUNCTIONAL KNOWLEDGE
				if(candidate.getProfessionalInfo().getFunctional() != null && candidate.getProfessionalInfo().getFunctional() != "") {
					criteria.add(Restrictions.disjunction().add(Restrictions.ilike("info_alias.functional", "%" + candidate.getProfessionalInfo().getFunctional() + "%")));
				}
			}
		}

		return criteria.list();
	}

	@Override
	public Integer createOrUpdateCandidate(Candidate c) {
		Session session = this.sessionFactory.getCurrentSession();
		if(c.getId() == null) {
			session.persist(c);
			session.flush();
		} else {
			session.update(c);
		}
		return c.getId();
	}

	@Override
	public Candidate getCandidateById(int id) {
		Session session = this.sessionFactory.getCurrentSession();      
		Candidate c = (Candidate) session.load(Candidate.class, new Integer(id));
		return c;
	}

	@Override
	public void removeCandidate(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Candidate c = (Candidate) session.load(Candidate.class, new Integer(id));
		if(null != c){
			session.delete(c);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Locality> lislLocalities() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Locality> localsList = session.createQuery("from Locality").list();
		return localsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Language> lisLanguages() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Language> languageList = session.createQuery("from Language").list();
		return languageList;
	}
}