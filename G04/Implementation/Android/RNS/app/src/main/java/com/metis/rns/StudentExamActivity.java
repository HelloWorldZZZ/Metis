package com.metis.rns;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.metis.rns.po.Question;
import com.metis.rns.po.Student;
import com.metis.rns.utils.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentExamActivity extends BaseActivity {
    private Student mStudent;
    private Button mButton;
    private int questionNo;
    private TextView mQuestion, mAnswer;
    private JSONObject studentAnswer;
    private MyCountDownTimer MC;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = StudentExamActivity.this;
        setContentView(R.layout.activity_student_exam);
        mStudent = (Student) getIntent().getSerializableExtra("student");
        setActionBarTitle("复试-----" + mStudent.getStudent_name());
        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.complete);
        mQuestion = (TextView) findViewById(R.id.question);
        mAnswer = (TextView) findViewById(R.id.answer);
        mButton.setOnClickListener(new UploadOnclickListener());
        MC = new MyCountDownTimer(30000, 1000);
        questionNo = 1;
        studentAnswer = new JSONObject();
        try {
            studentAnswer.put("enroll_num", mStudent.getEnroll_num());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initQuestion(questionNo);
    }

    private void initQuestion(int number) {
        MC.start();
        mAnswer.setText("");
        Question question = mStudent.getQuestionHashMap().get(number);
        mQuestion.setText(question.getQuestion_content());
    }

    private void uploadAnswer() {
        final ProgressDialog pDialog = ProgressDialog.show(this, null, "提交中...", true, true);
        pDialog.setCancelable(false);
        String url = getString(R.string.api_url) + "saveAnswer.php";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("answer", studentAnswer.toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    class UploadOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            upload();
        }
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p/>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p/>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            upload();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mButton.setText("提交(剩余" + millisUntilFinished / 1000 + "s)");
        }
    }

    private void upload() {
        MC.cancel();
        String answer = mAnswer.getText().toString();
        try {
            switch (questionNo) {
                case 1:
                    studentAnswer.put("en_answer", answer);
                    questionNo++;
                    break;
                case 2:
                    studentAnswer.put("pro_answer", answer);
                    questionNo++;
                    break;
                case 3:
                    studentAnswer.put("peo_answer", answer);
                    questionNo++;
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (questionNo > 3) {
            uploadAnswer();
        } else {
            Toast.makeText(mContext,"下一题", Toast.LENGTH_SHORT).show();
            initQuestion(questionNo);
        }
    }

}
