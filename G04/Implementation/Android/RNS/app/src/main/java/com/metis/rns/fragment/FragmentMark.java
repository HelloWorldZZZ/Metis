package com.metis.rns.fragment;

/**
 * Created by triplez on 16-3-17.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.metis.rns.R;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMark extends Fragment {
    private View mRootView;
    private ProgressDialog pDialog;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private JSONArray studentJson;
    private ArrayList<Student> studentList;

    private Thread studentThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("class_no"
                    , getActivity().getIntent().getStringExtra("class_no")));
            doPost(params);
            Looper.loop();
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_mark,container,false);
        studentList = new ArrayList<>();
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.studentList);
        new Thread(studentThread).start();
        pDialog = ProgressDialog.show(getActivity(), null, "数据加载中,请稍后...", true, true);
        pDialog.setCancelable(false);
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                initView();
            }
        });
        return mRootView;
    }

    private void initView() {
        initStudentList();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(getActivity(), studentList);
        mRecyclerView.setAdapter(myAdapter);
    }

    private void initStudentList() {
        studentList.clear();
        for (int i = 0; i < studentJson.length(); i++) {
            try {
                JSONObject jo = studentJson.getJSONObject(i);
                Student student = new Student(jo.getString("student_school"), jo.getString("student_birthday"),
                        jo.getString("student_nation"), jo.getString("student_sex"), jo.getString("student_type_name"),
                        jo.getString("student_name"), jo.getString("temp_testid"), jo.getString("test_num"));
                studentList.add(student);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void doPost(ArrayList params) {
        String url = "http://metisapi.applinzi.com/getStudentList.php";
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
                studentJson = new JSONArray(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pDialog.dismiss();
    }

    public class Student{
        String test_num;
        String temp_testid;
        String student_name;
        String student_type_name;
        String student_sex;
        String student_nation;
        String student_birthday;
        String student_school;

        public Student(String student_school, String student_birthday, String student_nation,
                       String student_sex, String student_type_name, String student_name,
                       String temp_testid, String test_num) {
            this.student_school = student_school;
            this.student_birthday = student_birthday;
            this.student_nation = student_nation;
            this.student_sex = student_sex;
            this.student_type_name = student_type_name;
            this.student_name = student_name;
            this.temp_testid = temp_testid;
            this.test_num = test_num;
        }
    }

    public class MyAdapter
            extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {

        private ArrayList<Student> students;

        private Context mContext;

        public MyAdapter( Context context , ArrayList<Student> students)
        {
            this.mContext = context;
            this.students = students;
        }

        @Override
        public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
        {
            // 给ViewHolder设置布局文件
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_student, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder, int i )
        {
            // 给ViewHolder设置元素
            Student p = students.get(i);
            viewHolder.name.setText(p.student_name);
            viewHolder.testid.setText(p.temp_testid);
        }

        @Override
        public int getItemCount()
        {
            // 返回数据总数
            return students == null ? 0 : students.size();
        }

        // 重写的自定义ViewHolder
        public class ViewHolder
                extends RecyclerView.ViewHolder
        {
            public TextView name, testid;


            public ViewHolder( View v )
            {
                super(v);
                name = (TextView) v.findViewById(R.id.studentName);
                testid = (TextView) v.findViewById(R.id.studentNo);
            }
        }
    }

}