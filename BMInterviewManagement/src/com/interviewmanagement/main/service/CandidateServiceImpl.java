package com.interviewmanagement.main.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interviewmanagement.main.dao.CandidateDAO;
import com.interviewmanagement.main.dao.RefDataDAO;
import com.interviewmanagement.main.dao.UsersDAO;
import com.interviewmanagement.main.model.Candidate;
import com.interviewmanagement.main.model.Language;
import com.interviewmanagement.main.model.Locality;
import com.interviewmanagement.main.model.RefData;
import com.interviewmanagement.main.model.User;

@Service
public class CandidateServiceImpl implements CandidateService {

	private CandidateDAO candidateDAO;
	private UsersDAO usersDAO;
	private RefDataDAO refDataDAO;

	public void setCandidateDAO(CandidateDAO candidateDAO) {
		this.candidateDAO = candidateDAO;
	}
	
	public void setRefDataDAO(RefDataDAO refDataDAO) {
		this.refDataDAO = refDataDAO;
	}

	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	@Override
	@Transactional
	public List<Candidate> listCandidate(Candidate candidate) {
		return this.candidateDAO.listCandidate(candidate);
	}

	@Override
	@Transactional
	public Integer createOdUpdateCandidate(Candidate c) {
		return this.candidateDAO.createOrUpdateCandidate(c);
	}

	@Override
	@Transactional
	public Candidate getCandidateById(int id) {
		return this.candidateDAO.getCandidateById(id);
	}

	@Override
	@Transactional
	public void removeCandidate(int id) {
		this.candidateDAO.removeCandidate(id);
	}

	@Override
	@Transactional
	public List<Locality> lislLocalities() {
		return this.candidateDAO.lislLocalities();
	}

	@Override
	@Transactional
	public List<Language> lisLanguages() {
		return this.candidateDAO.lisLanguages();
	}

	@Override
	@Transactional
	public List<User> listUsers() {
		return this.usersDAO.listUsers();
	}

	@Override
	@Transactional
	public List<RefData> listRefData(String field) {
		return this.refDataDAO.listRefData(field);
	}

	@Override
	@Transactional
	public String getUserFullName(User user) {
		User retObj = this.usersDAO.getUserByUsername(user);
		return retObj.getFullName();
	}

	@Override
	@Transactional
	public User getUserById(int id) {
		return this.usersDAO.getUserById(id);
	}

	@Override
	@Transactional
	public Integer createOdUpdateUser(User user) {
		return this.usersDAO.createOrUpdateUser(user);
	}

	@Override
	@Transactional
	public void removeUser(int id) {
		this.usersDAO.removeUser(id);
	}

}
