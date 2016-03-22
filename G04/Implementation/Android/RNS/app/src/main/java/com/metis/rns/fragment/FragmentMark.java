package com.metis.rns.fragment;

/**
 * Created by triplez on 16-3-17.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.metis.rns.R;
import com.metis.rns.po.Exam;
import com.metis.rns.po.Student;
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
public class FragmentMark extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private JSONArray studentJson;
    private ArrayList<Student> studentList;
    private Exam mExam;
    private SharedPreferences examPreferences;
    private SharedPreferences.Editor examEditor;
    private boolean hasImpressMarked = false;
    private boolean hasFinalMarked = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_mark,container,false);
        mExam = (Exam) getActivity().getIntent().getSerializableExtra("examInfo");
        examPreferences = getActivity().getSharedPreferences(mExam.getTest_id()+mExam.getClass_id(), Context.MODE_PRIVATE);
        examEditor = examPreferences.edit();
        hasImpressMarked = examPreferences.getBoolean("hasImpressMarked", false);
        hasFinalMarked = examPreferences.getBoolean("hasFinalMarked", false);
        studentJson = mExam.getStudent_list();
        studentList = new ArrayList<>();
        initView();
        return mRootView;
    }

    private void initView() {
        initStudentList();
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(getActivity(), studentList);
        mRecyclerView.setAdapter(myAdapter);
        FloatingActionButton btnUpload = (FloatingActionButton) mRootView.findViewById(R.id.upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasFinalMarked) {
                    if (canUpload()) {
                        showUploadDialog();
                    } else {
                        Toast.makeText(getActivity(), "请先打完所有学生的分数", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "打分完毕,请退出系统", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initStudentList() {
        studentList.clear();
        for (int i = 0; i < studentJson.length(); i++) {
            try {
                JSONObject jo = studentJson.getJSONObject(i);
                Student student = new Student(jo.getString("enroll_num"), jo.getString("student_school"),
                        jo.getString("student_birthday"), jo.getString("student_nation"), jo.getString("student_sex"),
                        jo.getString("student_name"), jo.getString("test_temp_id"), jo.getString("test_num"));
                SharedPreferences studentPreferences = getActivity().getSharedPreferences(
                        "student_"+jo.getString("enroll_num")+mExam.getSubject_id(), Context.MODE_PRIVATE);
                int impressScore = studentPreferences.getInt("impressScore", -1);
                int finalScore = studentPreferences.getInt("finalScore", -1);
                student.setImpress_score(impressScore);
                student.setFinal_score(finalScore);
                studentList.add(student);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            final Student p = students.get(i);
            viewHolder.name.setText(p.student_name);
            if (p.getImpress_score() != -1) {
                viewHolder.impressScore.setText("印象分: " + p.getImpress_score());
            }
            if (p.getFinal_score() != -1) {
                viewHolder.finalScore.setText("考试分: " + p.getFinal_score());
            }
            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!hasFinalMarked) {
                        showMarkDialog(p);
                    } else {
                        Toast.makeText(getActivity(), "打分完毕,请退出系统", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
            public TextView name, impressScore, finalScore;
            public View rootView;


            public ViewHolder( View v )
            {
                super(v);
                rootView = v;
                name = (TextView) v.findViewById(R.id.studentName);
                impressScore = (TextView) v.findViewById(R.id.impressScore);
                finalScore = (TextView) v.findViewById(R.id.finalScore);
            }
        }
    }

    private void showMarkDialog(final Student student) {
        SharedPreferences studentPreferences = getActivity().getSharedPreferences(
                "student_"+student.enroll_num+mExam.getSubject_id(), Context.MODE_PRIVATE);
        final SharedPreferences.Editor studentEditor = studentPreferences.edit();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View markView = inflater.inflate(R.layout.layout_mark, null);
        TextView s_name = (TextView) markView.findViewById(R.id.s_name);
        TextView s_sex = (TextView)markView.findViewById(R.id.s_sex);
        TextView s_enroll_num = (TextView)markView.findViewById(R.id.s_enroll_num);
        TextView s_nation = (TextView)markView.findViewById(R.id.s_nation);
        TextView s_birthday = (TextView)markView.findViewById(R.id.s_birthday);
        TextView s_school = (TextView)markView.findViewById(R.id.s_school);
        s_name.setText(student.student_name);
        s_sex.setText(student.student_sex);
        s_enroll_num.setText(student.enroll_num);
        s_nation.setText(student.student_nation);
        s_birthday.setText(student.student_birthday);
        s_school.setText(student.student_school);

        LinearLayout impressView = (LinearLayout)markView.findViewById(R.id.t_impress);
        LinearLayout finalView = (LinearLayout)markView.findViewById(R.id.t_final);
        final NumberPicker impressPicker = (NumberPicker)markView.findViewById(R.id.picker_impress);
        final NumberPicker finalPicker = (NumberPicker)markView.findViewById(R.id.picker_final);
        if (!hasImpressMarked) {
            finalView.setVisibility(View.GONE);
            impressPicker.setMaxValue(10);
            impressPicker.setMinValue(0);
            if (student.getImpress_score() != -1) {
                impressPicker.setValue(student.getImpress_score());
            } else {
                impressPicker.setValue(6);
            }
        } else {
            impressView.setVisibility(View.GONE);
            finalPicker.setMaxValue(90);
            finalPicker.setMinValue(0);
            if (student.getFinal_score() != -1) {
                finalPicker.setValue(student.getFinal_score());
            } else {
                finalPicker.setValue(60);
            }
        }
        AlertDialog markDialog = new AlertDialog.Builder(getActivity())
                .setPositiveButton("确定打分", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!hasImpressMarked) {
                            int impressScore = impressPicker.getValue();
                            studentEditor.putInt("impressScore",impressScore);
                            student.setImpress_score(impressScore);
                        } else {
                            int finalScore = finalPicker.getValue();
                            studentEditor.putInt("finalScore",finalScore);
                            student.setFinal_score(finalScore);
                        }
                        studentEditor.commit();
                        myAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", null)
                .setView(markView)
                .create();
        markDialog.show();
    }

    private boolean canUpload() {
        for (Student student : studentList) {
            if (!hasImpressMarked) {
                if (student.getImpress_score() == -1)
                    return false;
            } else {
                if (student.getFinal_score() == -1)
                    return false;
            }
        }
        return true;
    }

    private void showUploadDialog() {
        AlertDialog uploadDialog = new AlertDialog.Builder(getActivity())
                .setMessage("确定提交分数?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (! hasImpressMarked) {
                            hasImpressMarked = true;
                            examEditor.putBoolean("hasImpressMarked",true);
                            examEditor.commit();
                            Toast.makeText(getActivity(), "印象分提交成功,请打考试分", Toast.LENGTH_SHORT).show();
                        } else {
                            hasFinalMarked = true;
                            examEditor.putBoolean("hasFinalMarked",true);
                            examEditor.commit();
                            saveExamInfo();
                            Toast.makeText(getActivity(), "考试分提交成功,请注销", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        uploadDialog.show();
    }

    private void saveExamInfo() {
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("exam", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        String list = mSharedPreferences.getString("examList", "[]");
        try {
            JSONArray examList = new JSONArray(list);

            JSONObject examItem = new JSONObject();
            examItem.put("subject_id", mExam.getSubject_id());
            examItem.put("subject_name", mExam.getSubject_name());
            examItem.put("expert_id", mExam.getExpert_id());
            examItem.put("expert_name", mExam.getExpert_name());
            examItem.put("create_time", Utils.getDate());
            JSONArray markList = new JSONArray();
            for (Student student : studentList){
                JSONObject jsonMark = new JSONObject();
                jsonMark.put("enroll_num", student.enroll_num);
                jsonMark.put("student_name", student.student_name);
                jsonMark.put("test_temp_id", student.temp_testid);
                jsonMark.put("mark", student.getImpress_score() + student.getFinal_score());
                markList.put(jsonMark);
            }
            examItem.put("markList",markList);
            examList.put(examItem);

            mEditor.putString("examList", examList.toString());
            mEditor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}