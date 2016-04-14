package com.metis.rns;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.metis.rns.po.Exam;
import com.metis.rns.po.Question;
import com.metis.rns.po.Student;
import com.metis.rns.utils.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ExamActivity extends BaseActivity {
    private Exam mExam;
    private Context mContext;
    private HashMap<Integer, Student> mStudentMap;
    private int mOrder, mStudentAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_exam);
        setActionBarTitle("复试");
        String examInfo = getIntent().getStringExtra("examInfo");
        mOrder = 1;
        initExam(examInfo);
        initView(mStudentMap.get(mOrder));
    }

    private void initView(final Student student) {
        TextView classNo = (TextView) findViewById(R.id.classNo);
        classNo.setText(mExam.getClass_no());
        TextView examType = (TextView) findViewById(R.id.examType);
        examType.setText(mExam.getSub_type_name());
        TextView examExpert = (TextView) findViewById(R.id.examExpert);
        examExpert.setText(mExam.getExpert_name());
        Button startBtn = (Button) findViewById(R.id.startExam);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StudentExamActivity.class);
                intent.putExtra("student", student);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initExam(String info) {
        try {
            JSONObject examJson = new JSONObject(info);
            mExam = new Exam();
            mExam.setTest_id(examJson.getString("test_id"));
            mExam.setClass_id(examJson.getString("class_id"));
            mExam.setClass_no(examJson.getString("class_no"));
            mExam.setSub_type_name(examJson.getString("sub_type_name"));
            mExam.setExpert_id(examJson.getString("expert_id"));
            mExam.setExpert_name(examJson.getString("expert_name"));
            mExam.setStart_time(examJson.getString("start_time"));
            mExam.setEnd_time(examJson.getString("end_time"));
            mExam.setStatus(examJson.getInt("status"));
            mExam.setStudent_list(examJson.getJSONArray("student_list"));
            mStudentMap = new HashMap<>();
            initStudentList(mExam.getStudent_list());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initStudentList(JSONArray studentArray) {
        mStudentMap.clear();
        mStudentAmount = studentArray.length();
        for (int i = 0; i < mStudentAmount; i++) {
            try {
                JSONObject studentJson = studentArray.getJSONObject(i);
                Student student = new Student();
                student.setEnroll_num(studentJson.getString("enroll_num"));
                student.setTest_num(studentJson.getString("test_num"));
                student.setTemp_testid(studentJson.getString("test_temp_id"));
                student.setStudent_name(studentJson.getString("student_name"));
                JSONArray questions = studentJson.getJSONArray("question");
                HashMap<Integer, Question> questionHashMap = new HashMap<>();
                for (int j = 0; j < questions.length(); j++) {
                    JSONObject questionJson = questions.getJSONObject(j);
                    Question question = new Question(questionJson.getString("question_id"),
                            questionJson.getString("question_type_id"), questionJson.getString("question_content"));
                    questionHashMap.put(Integer.parseInt(questionJson.getString("question_type_id")),
                            question);
                }
                student.setQuestionHashMap(questionHashMap);
                mStudentMap.put(Integer.parseInt(student.getTemp_testid()), student);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ++mOrder;
            if (mOrder <= mStudentAmount) {
                initView(mStudentMap.get(mOrder));
            } else {
                Toast.makeText(mContext, "全部考生复试结束", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}