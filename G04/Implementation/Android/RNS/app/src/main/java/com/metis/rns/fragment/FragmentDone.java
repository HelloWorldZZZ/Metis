package com.metis.rns.fragment;

/**
 * Created by triplez on 16-3-17.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.metis.rns.R;
import com.metis.rns.po.Mark;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDone extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private JSONArray markJson;
    private ArrayList<Mark> markList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_done,container,false);
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("done", Context.MODE_PRIVATE);
        markList = new ArrayList<>();
        try {
            markJson = new JSONArray(mSharedPreferences.getString("doneList","[]"));
            initView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mRootView;
    }

    private void initView() {
        initMarkList();
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(getActivity(), markList);
        mRecyclerView.setAdapter(myAdapter);
        FloatingActionButton btnUpload = (FloatingActionButton) mRootView.findViewById(R.id.upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearDialog();
            }
        });
    }

    private void initMarkList() {
        markList.clear();
        for (int i = 0; i < markJson.length(); i++) {
            try {
                JSONObject jo = markJson.getJSONObject(i);
                Mark mark = new Mark(jo.getString("subject_id"), jo.getString("subject_name"),
                        jo.getString("expert_id"), jo.getString("expert_name"),
                         jo.getString("create_time"), new JSONArray(jo.getString("markList")));
                markList.add(mark);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class MyAdapter
            extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {

        private ArrayList<Mark> marks;

        private Context mContext;

        public MyAdapter( Context context , ArrayList<Mark> marks)
        {
            this.mContext = context;
            this.marks = marks;
        }

        @Override
        public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
        {
            // 给ViewHolder设置布局文件
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_mark, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder, int i )
        {
            // 给ViewHolder设置元素
            final Mark p = marks.get(i);
            viewHolder.subject_name.setText(p.getSubject_name());
            viewHolder.expert_name.setText(p.getExpert_name());
            viewHolder.date.setText(p.getCreateTime());
            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showStudentMarkDialog(p);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            // 返回数据总数
            return marks == null ? 0 : marks.size();
        }

        // 重写的自定义ViewHolder
        public class ViewHolder
                extends RecyclerView.ViewHolder
        {
            public TextView subject_name, expert_name, date;
            public View rootView;


            public ViewHolder( View v )
            {
                super(v);
                rootView = v;
                subject_name = (TextView) v.findViewById(R.id.subjectName);
                expert_name = (TextView) v.findViewById(R.id.expertName);
                date = (TextView) v.findViewById(R.id.date);
            }
        }
    }

    private void showStudentMarkDialog(Mark mark) {
        StringBuilder sb = new StringBuilder();
        JSONArray markList = mark.getMarkList();
        for (int i = 0; i < markList.length(); i++) {
            try {
                JSONObject markItem = markList.getJSONObject(i);
                sb.append("姓名: ");
                sb.append(markItem.get("student_name"));
                sb.append("   印象分: ");
                sb.append(markItem.get("impression_mark"));
                sb.append("   最终分: ");
                sb.append(markItem.get("mark"));
                sb.append("\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AlertDialog studentMarkDialog = new AlertDialog.Builder(getActivity())
                .setTitle(mark.getSubject_name())
                .setMessage(sb)
                .setPositiveButton("确定", null)
                .create();
        studentMarkDialog.show();
    }

    private void showClearDialog() {
        AlertDialog uploadDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.app_name))
                .setMessage("清除本地数据?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(
                                "done", Context.MODE_PRIVATE);
                        mSharedPreferences.edit().clear().commit();
                        markList.clear();
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"已清空数据",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        uploadDialog.show();
    }

}