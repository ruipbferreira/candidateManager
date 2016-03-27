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
    List<Locality> lislLocalities();
    List<Language> lisLanguages();
    List<User> listUsers();
    List<RefData> listRefData(String field);
}
