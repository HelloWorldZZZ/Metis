package com.metis.rns.po;

import org.json.JSONArray;

/**
 * Created by triplez on 16-3-22.
 */
public class Mark {
    private String subject_id;
    private String subject_name;
    private String expert_id;
    private String expert_name;
    private String createTime;
    private JSONArray markList;

    public Mark(String subject_id, String subject_name, String expert_id, String expert_name,
                String createTime, JSONArray markList) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.expert_id = expert_id;
        this.expert_name = expert_name;
        this.createTime = createTime;
        this.markList = markList;
    }

    public Mark() {
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getExpert_id() {
        return expert_id;
    }

    public void setExpert_id(String expert_id) {
        this.expert_id = expert_id;
    }

    public String getExpert_name() {
        return expert_name;
    }

    public void setExpert_name(String expert_name) {
        this.expert_name = expert_name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public JSONArray getMarkList() {
        return markList;
    }

    public void setMarkList(JSONArray markList) {
        this.markList = markList;
    }
}
