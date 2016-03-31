package com.interviewmanagement.main.service;

import java.util.List;

import com.interviewmanagement.main.model.Candidate;
import com.interviewmanagement.main.model.Language;
import com.interviewmanagement.main.model.Locality;
import com.interviewmanagement.main.model.RefData;
import com.interviewmanagement.main.model.User;

public interface CandidateService {
	public String getUserFullName(User user);
	public Integer createOdUpdateCandidate(Candidate c);
    public List<Candidate> listCandidate(Candidate candidate);
    public Candidate getCandidateById(int id);
    public void removeCandidate(int id);
    public List<Locality> lislLocalities();
    public List<Language> lisLanguages();
    public List<User> listUsers();
    public List<RefData> listRefData(String field);
    public User getUserById(int id);
	public Integer createOdUpdateUser(User user);
	public void removeUser(int id);
}
