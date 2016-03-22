package com.interviewmanagement.main.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CANDIDATE")
public class Candidate {

	public static final String FIELD_ID = "id";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_PHONE = "phone";
	public static final String FIELD_LEVEL = "level";
	public static final String FIELD_BIRTHDATE = "birthDate";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_LOCAL = "local";
	public static final String FIELD_LANGUAGE = "language";
	public static final String FIELD_USER = "user";
	public static final String FIELD_INFO = "professionalInfo";

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String phone;
	private String level;
	@ManyToOne
	@JoinColumn(name = "localId")
	private Locality local;
	@ManyToOne
	@JoinColumn(name = "languageId")
	private Language language;
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	private Date birthDate;
	private String email;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "infoId")
	private CandidateProfessionalInfo professionalInfo;
	private String filename;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Locality getLocal() {
		return local;
	}
	public void setLocal(Locality local) {
		this.local = local;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public CandidateProfessionalInfo getProfessionalInfo() {
		return professionalInfo;
	}
	public void setProfessionalInfo(CandidateProfessionalInfo professionalInfo) {
		this.professionalInfo = professionalInfo;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
