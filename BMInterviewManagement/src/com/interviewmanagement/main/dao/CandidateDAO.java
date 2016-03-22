package com.interviewmanagement.main.dao;

import java.util.List;

import com.interviewmanagement.main.model.Candidate;
import com.interviewmanagement.main.model.Language;
import com.interviewmanagement.main.model.Locality;

public interface CandidateDAO {
	public Integer createOrUpdateCandidate(Candidate c);
	public List<Candidate> listCandidate(Candidate candidate);
	public Candidate getCandidateById(int id);
	public void removeCandidate(int id);
	List<Locality> lislLocalities();
    List<Language> lisLanguages();
}
