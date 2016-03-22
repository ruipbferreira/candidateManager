package com.interviewmanagement.main.utils;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;

import com.interviewmanagement.main.model.Locality;

@Component
public class LocalityEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) {
		if(text != null && text != "") {
			try {
				Locality l = new Locality();
				l.setLocalId(Integer.valueOf(text));
				this.setValue(l);
			} catch(Exception e) {
				this.setValue(new Locality());
			}
		}
	}

}
