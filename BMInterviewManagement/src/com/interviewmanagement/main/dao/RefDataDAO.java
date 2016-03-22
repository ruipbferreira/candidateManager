package com.interviewmanagement.main.dao;

import java.util.List;

import com.interviewmanagement.main.model.RefData;

public interface RefDataDAO {
    List<RefData> listRefData(String field);
}
