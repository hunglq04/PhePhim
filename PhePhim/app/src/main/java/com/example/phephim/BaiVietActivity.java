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

import com.example.adapter.BaiVietFragmentAdapter;
import com.example.adapter.UserFragmentAdapter;
import com.example.util.LoginDialog;
import com.squareup.picasso.Picasso;

public class BaiVietActivity extends AppCompatActivity {

    ViewPager vpBaiViet;
    TabLayout tlBaiViet;
    ImageView imgLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_viet);

        addControl();
        addToolbar();
    }

    private void addControl() {
        vpBaiViet = findViewById(R.id.vpBaiViet);
        vpBaiViet.setAdapter(new BaiVietFragmentAdapter(getSupportFragmentManager()));
        tlBaiViet = findViewById(R.id.tlBaiViet);
        tlBaiViet.setupWithViewPager(vpBaiViet);
    }

    public void addToolbar() {
        ImageView imgSearch, imgPost, imgHome;
        TextView txtPhim, txtBaiViet;
        final LoginDialog loginDialog = new LoginDialog(BaiVietActivity.this);

        txtPhim = findViewById(R.id.txtPhimToolbar);
        txtBaiViet = findViewById(R.id.txtBaiVetToolbar);
        imgLogin = findViewById(R.id.imgLoginToolbar);
        imgHome = findViewById(R.id.imgHomeToolbar);
        imgSearch = findViewById(R.id.imgSearchToolbar);
        imgPost = findViewById(R.id.imgDangBaiToolbar);

        txtPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PhimActivity.class));
            }
        });

        if(LoginActivity.user != null){
            Picasso.get()
                    .load(LoginActivity.user.getAvatar())
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgLogin);
        } else {
            imgLogin.setImageResource(R.drawable.login);
        }

        imgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginActivity.user == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                }
            }
        });

        txtBaiViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PhimActivity.class));
            }
        });

        imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginActivity.user == null) {
                    Toast.makeText(getApplicationContext(), "Bạn cần đăng nhập để đăng bài.",
                            Toast.LENGTH_LONG).show();
                    loginDialog.show();
                } else {
                    startActivity(new Intent(getApplicationContext(), DangBaiVietActivity.class));
                }
            }
        });

        loginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(LoginActivity.user != null) {
                    startActivity(new Intent(getApplicationContext(), DangBaiVietActivity.class));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        addControl();
        addToolbar();
    }
}
