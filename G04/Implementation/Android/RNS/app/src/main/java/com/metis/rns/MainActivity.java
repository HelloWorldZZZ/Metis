package com.metis.rns;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.metis.rns.fragment.FragmentDone;
import com.metis.rns.fragment.FragmentMark;
import com.metis.rns.fragment.FragmentSettings;
import com.metis.rns.fragment.FragmentUpload;
import com.metis.rns.po.Exam;
import com.metis.rns.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private Context mContext;
    private NavigationView mNavigationView;
    private LinearLayout mLoginView, mInfoView;
    private FrameLayout frameLayout;
    private ProgressDialog mLoginProgressDialog;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private JSONObject mInfoJson;
    private Toolbar mToolbar;
    private Exam mExam;
    private String expertUserName;
    private boolean isLogin;
    private int mRole;
    final int IDENTITY_EXPERT = 2;
    final int IDENTITY_ADMIN = 1;
    final int LOGIN_SUCCESS = 1;
    final int LOGIN_FAIL = 0;
    final int LOGIN_PAGE = 0;
    final int INFO_PAGE = 1;
    final int FRAME_PAGE = 2;
    ArrayList loginParams;

    private Thread loginThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            doPost(loginParams);
            Looper.loop();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        mContext = this;
        isLogin = false;
        initContentView();
        initLoginView();
    }

    private void initContentView() {
        mLoginView = (LinearLayout) findViewById(R.id.page_login);
        mInfoView = (LinearLayout) findViewById(R.id.page_info);
        frameLayout = (FrameLayout)findViewById(R.id.frame_content);
        //设置ToolBar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        //设置抽屉DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置导航栏NavigationView的点击事件
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    }

    private void initLoginView() {
        changViewVisibility(LOGIN_PAGE);
        Button btnLogin = (Button) mLoginView.findViewById(R.id.login);
        final EditText edT_username = (EditText) mLoginView.findViewById(R.id.username);
        final EditText edt_password = (EditText) mLoginView.findViewById(R.id.password);
        final RadioGroup group = (RadioGroup) mLoginView.findViewById(R.id.identity);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.hasNetwork(mContext)) {
                    Toast.makeText(mContext, "请检查网络连接", Toast.LENGTH_SHORT).show();
                } else {
                    String username = edT_username.getText().toString();
                    String password = edt_password.getText().toString();
                    int role;
                    if (group.getCheckedRadioButtonId() == R.id.expert) {
                        role = IDENTITY_EXPERT;
                        expertUserName = username;
                    } else {
                        role = IDENTITY_ADMIN;
                    }
                    mRole = role;
                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_SHORT).show();
                    } else {
                        loginParams = new ArrayList();
                        loginParams.add(new BasicNameValuePair("username", username));
                        loginParams.add(new BasicNameValuePair("password", password));
                        loginParams.add(new BasicNameValuePair("role", String.valueOf(role)));
                        mLoginProgressDialog = ProgressDialog.show(mContext, null, "正在登录", true, true);
                        mLoginProgressDialog.setCancelable(false);
                        new Thread(loginThread).start();
                    }
                }
            }
        });
    }

    private void changViewVisibility(int TYPE) {
        switch (TYPE) {
            case LOGIN_PAGE:
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                mLoginView.setVisibility(View.VISIBLE);
                mInfoView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                break;
            case INFO_PAGE:
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                mLoginView.setVisibility(View.GONE);
                mInfoView.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                break;
            case FRAME_PAGE:
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                mLoginView.setVisibility(View.GONE);
                mInfoView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initInfoView() {
        changViewVisibility(INFO_PAGE);
        try {
            TextView tvUserName = (TextView) findViewById(R.id.user_info_txt);
            ImageView ivPersonImg = (ImageView) findViewById(R.id.drawer_header_person_img);
            if (mRole == IDENTITY_ADMIN) {
                mNavigationView.setNavigationItemSelectedListener(new AdminNavigationViewListener());
                String adminName = mInfoJson.getString("admin_name");
                tvUserName.setText(adminName);
                ivPersonImg.setImageResource(R.mipmap.admin);
                mNavigationView.inflateMenu(R.menu.menu_admin);
                mInfoView.setVisibility(View.GONE);
                mDrawerLayout.openDrawer(Gravity.LEFT);
            } else if (mRole == IDENTITY_EXPERT) {
                initExamInfo(mInfoJson);
                mNavigationView.setNavigationItemSelectedListener(new ExpertNavigationViewListener());
                String expertName = mExam.getExpert_name();
                String class_no = mExam.getClass_no();
                String subject = mExam.getSubject_name();
                String type = mExam.getSub_type_name();
                String date = mExam.getDate();
                TextView classNo = (TextView)mInfoView.findViewById(R.id.classNo);
                classNo.setText(class_no);
                TextView examType = (TextView)mInfoView.findViewById(R.id.examType);
                examType.setText(type);
                TextView examSubject = (TextView)mInfoView.findViewById(R.id.examSubject);
                examSubject.setText(subject);
                TextView examDate = (TextView)mInfoView.findViewById(R.id.examDate);
                examDate.setText(date);
                tvUserName.setText(expertName);
                ivPersonImg.setImageResource(R.mipmap.professor);
                mNavigationView.inflateMenu(R.menu.menu_pro);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initExamInfo(JSONObject jsonObject) {
        try {
            String test_id = jsonObject.getString("test_id");
            String class_id = jsonObject.getString("class_id");
            String class_no = jsonObject.getString("class_no");
            String subject_id = jsonObject.getString("subject_id");
            String subject_name = jsonObject.getString("subject_name");
            String sub_type_name = jsonObject.getString("sub_type_name");
            String expert_id = jsonObject.getString("expert_id");
            String expert_name = jsonObject.getString("expert_name");
            String date = jsonObject.getString("Date");
            int subject_max_diff = jsonObject.getInt("subject_max_diff");
            JSONArray studentList = jsonObject.getJSONArray("student_list");
            mExam = new Exam(test_id, class_id, class_no, subject_id, subject_name,
                    sub_type_name, expert_id, expert_name, date, studentList, subject_max_diff);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void switchView(boolean isLogin) {
        if(isLogin) {
            initInfoView();
        } else {
            initLoginView();
        }
    }

    private void doPost(ArrayList params) {
        String url = "http://metis.applinzi.com/api/app/appLogin.php";
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            response = client.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                handleMessage(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(String value) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString("result", value);
        msg.setData(data);
        mHandler.sendMessage(msg);
        mLoginProgressDialog.dismiss();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String result = data.getString("result");
            try {
                mInfoJson = new JSONObject(result);
                int logined = mInfoJson.getInt("isLogin");
                if (logined == LOGIN_SUCCESS) {
                    isLogin = true;
                    switchView(true);
                } else if (logined == LOGIN_FAIL) {
                    isLogin = false;
                    Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isLogin) {
                showExitDialog();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitDialog() {
        AlertDialog exitDialog = new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.app_name))
                .setMessage("退出系统并注销?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        exitDialog.show();
    }

    private void showReExamDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View markView = inflater.inflate(R.layout.dialog_re_exam, null);
        final EditText expertName = (EditText) markView.findViewById(R.id.expert_name);
        AlertDialog reExamDialog = new AlertDialog.Builder(mContext)
                .setTitle("请输入复试信息")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String expert_name = expertName.getText().toString();
                        getReExamInfo(expert_name);
                    }
                })
                .setNegativeButton("取消", null)
                .setView(markView)
                .create();
        reExamDialog.show();
    }

    private void getReExamInfo(final String expertName) {
        final ProgressDialog pDialog = ProgressDialog.show(mContext, null, "获取复试信息...", true, true);
        pDialog.setCancelable(false);
        String url = "http://metis.applinzi.com/api/app/getRetestStudentList.php";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        startReExam(s);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                Toast.makeText(mContext, "信息获取失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("expert_username", expertName);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void startReExam(String s) {
        try {
            JSONObject info = new JSONObject(s);
            if (info.getInt("status") == 1) {
                Intent intent = new Intent(mContext, ExamActivity.class);
                intent.putExtra("examInfo", s);
                startActivity(intent);
            } else {
                Toast.makeText(mContext, "复试尚未开始", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class ExpertNavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mark:
                    changViewVisibility(FRAME_PAGE);
                    getIntent().putExtra("examInfo", mExam);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentMark()).commit();
                    mToolbar.setTitle("学生列表");
                    break;
                case R.id.remark:
                    Intent intent = new Intent(mContext, RemarkActivity.class);
                    intent.putExtra("examInfo", mInfoJson.toString());
                    startActivity(intent);
                    break;
                case R.id.logout:
                    showExitDialog();
                    break;
            }
            menuItem.setChecked(true);//点击了把它设为选中状态
            mDrawerLayout.closeDrawers();//关闭抽屉
            return true;
        }
    }

    private class AdminNavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.setting:
                    changViewVisibility(FRAME_PAGE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentSettings()).commit();
                    mToolbar.setTitle("设置服务器");
                    break;
                case R.id.upload:
                    changViewVisibility(FRAME_PAGE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentUpload()).commit();
                    mToolbar.setTitle("未上传条目");
                    break;
                case R.id.hasUpload:
                    changViewVisibility(FRAME_PAGE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentDone()).commit();
                    mToolbar.setTitle("已上传条目");
                    break;
                case R.id.reExam:
                    showReExamDialog();
                    break;
                case R.id.logout:
                    showExitDialog();
                    break;
            }
            menuItem.setChecked(true);//点击了把它设为选中状态
            mDrawerLayout.closeDrawers();//关闭抽屉
            return true;
        }
    }

}

