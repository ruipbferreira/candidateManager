package com.interviewmanagement.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PROFESSIONAL_INFO")
public class CandidateProfessionalInfo {
	
	public static final String FIELD_ID = "infoId";
	public static final String FIELD_SITUATION = "situation";
	public static final String FIELD_AVAILABILITY = "availability";
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_ARQ_DEV = "arqDev";
	public static final String FIELD_TECNOLOGY = "tecnology";
	public static final String FIELD_ANALISYS = "analisys";
	public static final String FIELD_FUNCIONAL = "functional";
	public static final String FIELD_PRESENTATION = "presentation";
	public static final String FIELD_PROFESSIONAL_OBJECTIVES = "professionalObjectives";
	public static final String FIELD_REMUNERATION = "remuneration";
	public static final String FIELD_COMMENTS = "comments";
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer infoId;
	
	private String situation;
	
	private String availability;
	
	private Integer year;
	
	@Column(name="ARQ_DEV")
	private String arqDev;
	
	private String tecnology;
	
	@Column(name="ANALISYS_COORD")
	private String analisys;
	
	@Column(name="FUNCIONAL_KNOWLEDGE")
	private String functional;
	
	private String presentation;
	
	@Column(name="PROF_OBJECTIVES")
	private String professionalObjectives;
	
	private String remuneration;
	
	private String comments;

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getArqDev() {
		return arqDev;
	}

	public void setArqDev(String arqDev) {
		this.arqDev = arqDev;
	}

	public String getTecnology() {
		return tecnology;
	}

	public void setTecnology(String tecnology) {
		this.tecnology = tecnology;
	}

	public String getAnalisys() {
		return analisys;
	}

	public void setAnalisys(String analisys) {
		this.analisys = analisys;
	}

	public String getFunctional() {
		return functional;
	}

	public void setFunctional(String functional) {
		this.functional = functional;
	}

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public String getProfessionalObjectives() {
		return professionalObjectives;
	}

	public void setProfessionalObjectives(String professionalObjectives) {
		this.professionalObjectives = professionalObjectives;
	}

	public String getRemuneration() {
		return remuneration;
	}

	public void setRemuneration(String remuneration) {
		this.remuneration = remuneration;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
