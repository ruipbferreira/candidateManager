package com.interviewmanagement.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REF_DATA")
public class RefData {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer refDataId;
	private String code;
	private String name;
	private String field;
	
	public RefData() {
	}
	
	public RefData(Integer id, String code) {
		this.refDataId = id;
		this.code = code;
	}

	public Integer getRefDataId() {
		return refDataId;
	}

	public void setRefDataId(Integer refDataId) {
		this.refDataId = refDataId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
