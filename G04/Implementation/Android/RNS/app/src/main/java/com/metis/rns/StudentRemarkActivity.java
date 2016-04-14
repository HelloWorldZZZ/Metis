package com.metis.rns;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.metis.rns.po.Student;
import com.metis.rns.utils.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentRemarkActivity extends BaseActivity {
    private Context mContext;
    private Student mStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = StudentRemarkActivity.this;
        setContentView(R.layout.activity_student_remark);
        mStudent = (Student) getIntent().getSerializableExtra("student");
        setActionBarTitle("复试打分");
        getStudentAnswer();
    }

    private void initView(String json) {
        try {
            JSONObject answerJson = new JSONObject(json);
            Button remarkBtn = (Button) findViewById(R.id.remarkBtn);
            TextView sName = (TextView) findViewById(R.id.s_name);
            TextView sEnrollName = (TextView) findViewById(R.id.s_enroll_num);
            TextView sQuestion1 = (TextView) findViewById(R.id.question1);
            TextView sAnswer1 = (TextView) findViewById(R.id.answer1);
            TextView sQuestion2 = (TextView) findViewById(R.id.question2);
            TextView sAnswer2 = (TextView) findViewById(R.id.answer2);
            TextView sQuestion3 = (TextView) findViewById(R.id.question3);
            TextView sAnswer3 = (TextView) findViewById(R.id.answer3);
            sName.setText(mStudent.getStudent_name());
            sEnrollName.setText(mStudent.getEnroll_num());
            sQuestion1.setText(answerJson.getString("en_question"));
            sQuestion2.setText(answerJson.getString("pro_question"));
            sQuestion3.setText(answerJson.getString("peo_question"));
            sAnswer1.setText(answerJson.getString("en_answer"));
            sAnswer2.setText(answerJson.getString("pro_answer"));
            sAnswer3.setText(answerJson.getString("peo_answer"));
            final NumberPicker remark1 = (NumberPicker) findViewById(R.id.remark1);
            final NumberPicker remark2 = (NumberPicker) findViewById(R.id.remark2);
            final NumberPicker remark3 = (NumberPicker) findViewById(R.id.remark3);
            remark1.setMaxValue(30);
            remark2.setMaxValue(40);
            remark3.setMaxValue(30);
            remark1.setMinValue(0);
            remark2.setMinValue(0);
            remark3.setMinValue(0);
            remarkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRemarkDialog(remark1.getValue(), remark2.getValue(), remark3.getValue());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showRemarkDialog(final int mark1, final int mark2, final int mark3) {
        AlertDialog remarkDialog = new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.app_name))
                .setMessage("确定打分?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String remarkString = "{\"enroll_num\":" + mStudent.getEnroll_num() +
                                ",\"retest_en_mark\":" + mark1 + ",\"retest_pro_mark\":" + mark2
                                + ",\"retest_peo_mark\":" + mark3 + "}";
                        uploadRemark(remarkString);
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        remarkDialog.show();
    }

    private void uploadRemark(final String remarkString) {
        final ProgressDialog pDialog = ProgressDialog.show(this, null, "提交中...", true, true);
        pDialog.setCancelable(false);
        String url = "http://metis.applinzi.com/api/app/saveRetestMark.php";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                Toast.makeText(mContext, "提交失败,请重试", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("retestMark", remarkString);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getStudentAnswer() {
        final ProgressDialog pDialog = ProgressDialog.show(this, null, "获取考生答卷...", true, true);
        pDialog.setCancelable(false);
        String url = "http://metis.applinzi.com/api/app/getAnswer.php";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject resultJson = new JSONObject(s);
                            int status = resultJson.getInt("status");
                            pDialog.dismiss();
                            if (status == 1) {
                                initView(s);
                            } else {
                                Toast.makeText(mContext, "考生尚未完成考试", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_CANCELED);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                Toast.makeText(mContext, "获取失败", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("enroll_num", mStudent.getEnroll_num());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
