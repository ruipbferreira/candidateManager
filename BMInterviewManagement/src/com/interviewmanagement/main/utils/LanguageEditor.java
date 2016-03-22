package com.interviewmanagement.main.utils;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;

import com.interviewmanagement.main.model.Language;

@Component
public class LanguageEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) {
		if(text != null && text != "") {
			try {
				Language l = new Language();
				l.setLanguageId(Integer.valueOf(text));
				this.setValue(l);
			} catch(Exception e) {
				this.setValue(new Language());
			}
		}
	}

}
