package com.metis.rns.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by triplez on 16-3-19.
 */
public class Student implements Serializable{
    public String enroll_num;
    public String test_num;
    public String temp_testid;
    public String student_name;
    public String student_sex;
    public String student_nation;
    public String student_birthday;
    public String student_school;
    public int impress_score;
    public int final_score;
    public HashMap<Integer, Question> questionHashMap;

    public Student() {
    }

    public Student(String enroll_num, String student_school, String student_birthday,
                   String student_nation, String student_sex, String student_name,
                   String temp_testid, String test_num) {
        this.enroll_num = enroll_num;
        this.student_school = student_school;
        this.student_birthday = student_birthday;
        this.student_nation = student_nation;
        this.student_sex = student_sex;
        this.student_name = student_name;
        this.temp_testid = temp_testid;
        this.test_num = test_num;
        this.impress_score = -1;
        this.final_score = -1;
    }

    public int getFinal_score() {
        return final_score;
    }

    public void setFinal_score(int final_score) {
        this.final_score = final_score;
    }

    public int getImpress_score() {
        return impress_score;
    }

    public void setImpress_score(int impress_score) {
        this.impress_score = impress_score;
    }

    public String getEnroll_num() {
        return enroll_num;
    }

    public void setEnroll_num(String enroll_num) {
        this.enroll_num = enroll_num;
    }

    public String getTest_num() {
        return test_num;
    }

    public void setTest_num(String test_num) {
        this.test_num = test_num;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getTemp_testid() {
        return temp_testid;
    }

    public void setTemp_testid(String temp_testid) {
        this.temp_testid = temp_testid;
    }

    public String getStudent_sex() {
        return student_sex;
    }

    public void setStudent_sex(String student_sex) {
        this.student_sex = student_sex;
    }

    public String getStudent_nation() {
        return student_nation;
    }

    public void setStudent_nation(String student_nation) {
        this.student_nation = student_nation;
    }

    public String getStudent_birthday() {
        return student_birthday;
    }

    public void setStudent_birthday(String student_birthday) {
        this.student_birthday = student_birthday;
    }

    public String getStudent_school() {
        return student_school;
    }

    public void setStudent_school(String student_school) {
        this.student_school = student_school;
    }

    public HashMap<Integer, Question> getQuestionHashMap() {
        return questionHashMap;
    }

    public void setQuestionHashMap(HashMap<Integer, Question> questionHashMap) {
        this.questionHashMap = questionHashMap;
    }
}
