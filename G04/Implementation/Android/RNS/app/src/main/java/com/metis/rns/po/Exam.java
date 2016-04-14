package com.metis.rns.po;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by triplez on 16-3-19.
 */
public class Exam implements Serializable {
    private String test_id;
    private String class_id;
    private String class_no;
    private String subject_id;
    private String subject_name;
    private String sub_type_name;
    private String expert_id;
    private String expert_name;
    private String date;
    private String start_time;
    private String end_time;
    private JSONArray student_list;
    private int subject_max_diff;
    private int status;

    public Exam() {
    }

    public Exam(String test_id, String class_id, String class_no, String subject_id,
                String subject_name, String sub_type_name, String expert_id, String expert_name,
                String date, JSONArray student_list, int subject_max_diff) {
        this.test_id = test_id;
        this.class_id = class_id;
        this.class_no = class_no;
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.sub_type_name = sub_type_name;
        this.expert_id = expert_id;
        this.expert_name = expert_name;
        this.date = date;
        this.student_list = student_list;
        this.subject_max_diff = subject_max_diff;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_no() {
        return class_no;
    }

    public void setClass_no(String class_no) {
        this.class_no = class_no;
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

    public String getSub_type_name() {
        return sub_type_name;
    }

    public void setSub_type_name(String sub_type_name) {
        this.sub_type_name = sub_type_name;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public JSONArray getStudent_list() {
        return student_list;
    }

    public void setStudent_list(JSONArray student_list) {
        this.student_list = student_list;
    }

    public int getSubject_max_diff() {
        return subject_max_diff;
    }

    public void setSubject_max_diff(int subject_max_diff) {
        this.subject_max_diff = subject_max_diff;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
