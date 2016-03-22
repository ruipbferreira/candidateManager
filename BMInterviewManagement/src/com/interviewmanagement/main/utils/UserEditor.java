package com.interviewmanagement.main.utils;

import java.beans.PropertyEditorSupport;

import org.springframework.stereotype.Component;

import com.interviewmanagement.main.model.User;

@Component
public class UserEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) {
		if(text != null && text != "") {
			User l = new User();
			try {
				l.setUserId(Integer.valueOf(text));
				this.setValue(l);
			} catch(Exception e) {
				this.setValue(new User());
			}
		}
	}

}
