package com.metis.rns.po;

/**
 * Created by triplez on 16-3-19.
 */
public class Student {
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

}
