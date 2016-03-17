package com.metis.rns;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.metis.rns.fragment.FragmentMark;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private Context mContext;
    private NavigationView mNavigationView;
    private LinearLayout mLoginView, mInfoView;
    private FrameLayout frameLayout;
    private ProgressDialog mLoginProgressDialog;
    private DrawerLayout mDrawerLayout;
    private JSONObject mInfoJson;
    private Toolbar mToolbar;
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
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
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
        final EditText edt_class_no = (EditText) mLoginView.findViewById(R.id.class_no);
        final RadioGroup group = (RadioGroup) mLoginView.findViewById(R.id.identity);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin) {
                    edt_class_no.setVisibility(View.GONE);
                } else if (checkedId == R.id.expert) {
                    edt_class_no.setVisibility(View.VISIBLE);
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.hasNetwork(mContext)) {
                    Toast.makeText(mContext, "请检查网络连接", Toast.LENGTH_SHORT).show();
                } else {
                    String username = edT_username.getText().toString();
                    String password = edt_password.getText().toString();
                    String class_no = edt_class_no.getText().toString();
                    int role;
                    if (group.getCheckedRadioButtonId() == R.id.expert) {
                        role = IDENTITY_EXPERT;
                    } else {
                        role = IDENTITY_ADMIN;
                    }
                    mRole = role;
                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_SHORT).show();
                    } else if (class_no.isEmpty() && mRole == IDENTITY_EXPERT) {
                        Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_SHORT).show();
                    } else {
                        loginParams = new ArrayList();
                        loginParams.add(new BasicNameValuePair("username", username));
                        loginParams.add(new BasicNameValuePair("password", password));
                        loginParams.add(new BasicNameValuePair("role", String.valueOf(role)));
                        loginParams.add(new BasicNameValuePair("class_no", class_no));
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
                mNavigationView.setVisibility(View.GONE);
                mLoginView.setVisibility(View.VISIBLE);
                mInfoView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                break;
            case INFO_PAGE:
                mNavigationView.setVisibility(View.VISIBLE);
                mLoginView.setVisibility(View.GONE);
                mInfoView.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                break;
            case FRAME_PAGE:
                mNavigationView.setVisibility(View.VISIBLE);
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
                String adminName = mInfoJson.getString("admin_account_name");
                tvUserName.setText(adminName);
                ivPersonImg.setImageResource(R.mipmap.admin);
                mNavigationView.inflateMenu(R.menu.menu_admin);
                mInfoView.setVisibility(View.GONE);
                mDrawerLayout.openDrawer(Gravity.LEFT);
            } else if (mRole == IDENTITY_EXPERT) {
                mNavigationView.setNavigationItemSelectedListener(new ExpertNavigationViewListener());
                String expertName = mInfoJson.getString("expert_name");
                String class_no = mInfoJson.getString("test_class_no");
                String subject = mInfoJson.getString("test_subject_name");
                String type = mInfoJson.getString("type_name");
                TextView classNo = (TextView)mInfoView.findViewById(R.id.classNo);
                classNo.setText(class_no);
                getIntent().putExtra("class_no", class_no);
                TextView examType = (TextView)mInfoView.findViewById(R.id.examType);
                examType.setText(type);
                TextView examSubject = (TextView)mInfoView.findViewById(R.id.examSubject);
                examSubject.setText(subject);
                tvUserName.setText(expertName);
                ivPersonImg.setImageResource(R.mipmap.professor);
                mNavigationView.inflateMenu(R.menu.menu_pro);
            }
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
        String url = "http://1.metisapi.applinzi.com/appLogin.php";
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

    private class ExpertNavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.info:
                    changViewVisibility(INFO_PAGE);
                    mToolbar.setTitle(getString(R.string.app_name));
                    break;
                case R.id.mark:
                    changViewVisibility(FRAME_PAGE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentMark()).commit();
                    mToolbar.setTitle("学生列表");
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
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentOne()).commit();
                    mToolbar.setTitle("设置服务器");
                    break;
                case R.id.mark:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentOne()).commit();
                    mToolbar.setTitle("成绩上传");
                    break;
                case R.id.logout:
                    mToolbar.setTitle(getString(R.string.app_name));
                    switchView(false);
                    break;
            }
            menuItem.setChecked(true);//点击了把它设为选中状态
            mDrawerLayout.closeDrawers();//关闭抽屉
            return true;
        }
    }

}

