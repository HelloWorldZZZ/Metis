package com.metis.rns.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.metis.rns.R;

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        mToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        mContainer = (FrameLayout) findViewById(R.id.base_container);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mContainer, true);
    }

    protected void setActionBarTitle(int resId) {
        setActionBarTitle(getString(resId));
    }

    protected void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
