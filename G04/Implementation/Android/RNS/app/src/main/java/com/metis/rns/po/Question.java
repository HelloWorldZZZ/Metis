package com.metis.rns.po;

import java.io.Serializable;

/**
 * Created by triplez on 16-4-7.
 */
public class Question implements Serializable{
    private String question_id;
    private String question_type_id;
    private String question_content;

    public Question() {
    }

    public Question(String question_type_id, String question_id, String question_content) {
        this.question_type_id = question_type_id;
        this.question_id = question_id;
        this.question_content = question_content;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_type_id() {
        return question_type_id;
    }

    public void setQuestion_type_id(String question_type_id) {
        this.question_type_id = question_type_id;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }
}
