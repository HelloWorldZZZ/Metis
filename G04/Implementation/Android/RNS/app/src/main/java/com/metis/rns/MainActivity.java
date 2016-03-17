package com.metis.rns;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.metis.rns.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
    private ProgressDialog mLoginProgressDialog;
    private DrawerLayout mDrawerLayout;
    private JSONObject mInfoJson;
    private int mRole;
    final int IDENTITY_EXPERT = 2;
    final int IDENTITY_ADMIN = 1;
    final int LOGIN_SUCCESS = 1;
    final int LOGIN_FAIL = 0;
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
        mLoginProgressDialog = new ProgressDialog(mContext);
        mLoginProgressDialog.setCancelable(false);
        initNavigationView();
        switchView(false);
    }

    private void initNavigationView() {
        mLoginView = (LinearLayout) findViewById(R.id.page_login);
        mInfoView = (LinearLayout) findViewById(R.id.page_info);
        //设置ToolBar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
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
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_one:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentOne()).commit();
                        mToolbar.setTitle("我的动态");
                        break;
                    case R.id.item_two:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentTwo()).commit();
                        mToolbar.setTitle("我的留言");
                        break;
                    case R.id.item_three:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentThree()).commit();
                        mToolbar.setTitle("附近的人");
                        break;
                }
                menuItem.setChecked(true);//点击了把它设为选中状态
                mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
    }

    private void initInfoView() {
        mNavigationView.setVisibility(View.VISIBLE);
        mLoginView.setVisibility(View.GONE);
        mInfoView.setVisibility(View.VISIBLE);
        mDrawerLayout.openDrawer(Gravity.LEFT);
        try {
            TextView tvUserName = (TextView) findViewById(R.id.user_info_txt);
            ImageView ivPersonImg = (ImageView) findViewById(R.id.drawer_header_person_img);
            if (mRole == IDENTITY_ADMIN) {
                String adminName = mInfoJson.getString("admin_account_name");
                tvUserName.setText(adminName);
                ivPersonImg.setImageResource(R.mipmap.admin);
                mNavigationView.inflateMenu(R.menu.menu_admin);
            } else if (mRole == IDENTITY_EXPERT) {
                String expertName = mInfoJson.getString("expert_name");
                String class_no = mInfoJson.getString("test_class_no");
                String subject = mInfoJson.getString("test_subject_name");
                String type = mInfoJson.getString("type_name");
                tvUserName.setText(expertName);
                ivPersonImg.setImageResource(R.mipmap.professor);
                mNavigationView.inflateMenu(R.menu.menu_pro);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initLoginView() {
        mNavigationView.setVisibility(View.GONE);
        mLoginView.setVisibility(View.VISIBLE);
        mInfoView.setVisibility(View.GONE);
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
                        if (!mLoginProgressDialog.isShowing()) {
                            mLoginProgressDialog = ProgressDialog.show(mContext, null, "正在登录", true, true);
                        }
                        new Thread(loginThread).start();
                    }
                }
            }
        });
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
                int isLogin = mInfoJson.getInt("isLogin");
                if (isLogin == LOGIN_SUCCESS) {
                    switchView(true);
                } else if (isLogin == LOGIN_FAIL) {
                    Toast.makeText(mContext, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}

