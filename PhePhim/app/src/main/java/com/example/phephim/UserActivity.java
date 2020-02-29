package com.example.phephim;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.UserFragmentAdapter;
import com.example.util.LoginDialog;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {
    ViewPager vpUser;
    TabLayout tlUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        addControl();
    }

    private void addControl() {
        vpUser = findViewById(R.id.vpUser);
        vpUser.setAdapter(new UserFragmentAdapter(getSupportFragmentManager()));
        tlUser = findViewById(R.id.tlUser);
        tlUser.setupWithViewPager(vpUser);
    }

}
