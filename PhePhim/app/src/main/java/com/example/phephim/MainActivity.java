package com.example.phephim;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.BaiVietAdapter;
import com.example.adapter.PhimAdapter;
import com.example.model.BaiViet;
import com.example.model.Phim;
import com.example.service.APIUtils;
import com.example.service.DataClient;
import com.example.util.LoginDialog;
import com.example.util.MyListView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.time.LocalDate.now;

public class MainActivity extends AppCompatActivity {

    GridView gvPhim;
    PhimAdapter phimAdapter;
    MyListView lvBaiViet;
    BaiVietAdapter baiVietAdapter;

    ImageView imgLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
        addPhim();
        addBaiViet();
    }

    private void addControls() {
        gvPhim = findViewById(R.id.gvPhimHotMain);
        phimAdapter = new PhimAdapter(this, R.layout.phim_hot_item_main);
        gvPhim.setAdapter(phimAdapter);

        lvBaiViet = findViewById(R.id.lvBaiVietHotMain);
        baiVietAdapter = new BaiVietAdapter(this, R.layout.bai_viet_item);
        lvBaiViet.setAdapter(baiVietAdapter);

        addToolbar();
    }

    public void addToolbar() {
        ImageView imgSearch, imgPost, imgHome;
        TextView txtPhim, txtBaiViet;
        final LoginDialog loginDialog = new LoginDialog(MainActivity.this);

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

        txtBaiViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BaiVietActivity.class));
            }
        });

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

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void addPhim() {
        DataClient dataClient = APIUtils.getData();
        Call<List<Phim>> callback = dataClient.getPhimHot("");
        callback.enqueue(new Callback<List<Phim>>() {
            @Override
            public void onResponse(Call<List<Phim>> call, Response<List<Phim>> response) {
                phimAdapter.clear();
                for (int i = 0; i < response.body().size(); i++){
                if(response.body().get(i).getDiem() > 0 )
                    phimAdapter.add(response.body().get(i));
                }

            }
            @Override
            public void onFailure(Call<List<Phim>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("QQQ", t.getMessage());
            }
        });
    }

    private void addBaiViet() {
        DataClient dataClient = APIUtils.getData();
        Call<List<BaiViet>> callback = dataClient.getBaiViet("");
        callback.enqueue(new Callback<List<BaiViet>>() {
            @Override
            public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                baiVietAdapter.clear();
                for (int i = 0; i < response.body().size(); i++){
                    if(i==6){
                        return;
                    }
                    baiVietAdapter.add(response.body().get(i));
                }
            }

            @Override
            public void onFailure(Call<List<BaiViet>> call, Throwable t) {
                Log.e("QQQ", t.getMessage());
            }
        });
    }

    public void openBaiVietActivity(View view) {
        startActivity(new Intent(getApplicationContext(), BaiVietActivity.class));
    }

    public void openPhimActivity(View view) {
        startActivity(new Intent(this, PhimActivity.class));
    }

    private void addEvents() {
        gvPhim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PhimDetailActivity.class);
                Phim phim = phimAdapter.getItem(position);
                intent.putExtra("Phim", phim);
                startActivity(intent);
            }
        });

        lvBaiViet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BaiVietDetailActivity.class);
                BaiViet baiViet = baiVietAdapter.getItem(position);
                intent.putExtra("BaiViet", baiViet);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        addPhim();
        addBaiViet();

        if(LoginActivity.user != null){
            Picasso.get()
                    .load(LoginActivity.user.getAvatar())
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgLogin);
        }
        else {
            imgLogin.setImageResource(R.drawable.login);
        }
    }

}
