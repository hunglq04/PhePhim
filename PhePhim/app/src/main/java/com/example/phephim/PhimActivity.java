package com.example.phephim;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.PhimAdapter;
import com.example.model.BaiViet;
import com.example.model.Phim;
import com.example.service.APIUtils;
import com.example.service.DataClient;
import com.example.util.LoginDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhimActivity extends AppCompatActivity {

    GridView gvPhim;
    PhimAdapter phimAdapter;
    EditText edtSearch;
    TextView txtNotFound;
    ImageView imgLogin;
    Spinner spTheLoai;
    List<Phim> phimList = new ArrayList<>();
    List<String> theLoaiList = new ArrayList<>();
    int maTheLoai = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phim);

        addControls();
        addEvents();
        getSearchResult(edtSearch.getText().toString(), maTheLoai);
        setupSpinnerData();
        addToolbar();
    }

    private void addControls() {
        gvPhim = findViewById(R.id.gvPhim);
        phimAdapter = new PhimAdapter(this, R.layout.phim_hot_item_main);
        gvPhim.setAdapter(phimAdapter);

        edtSearch = findViewById(R.id.edtSearchPhim);
        txtNotFound = findViewById(R.id.txtNotFoundPhim);
        spTheLoai = findViewById(R.id.spTheLoai);
    }

    private void addEvents() {
        gvPhim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PhimDetailActivity.class);
                Phim phim = phimAdapter.getItem(position);
                intent.putExtra("Phim", phim);
                startActivity(intent);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtNotFound.setText("");
                if (edtSearch.getText().toString().equals("")) {
                    getSearchResult(edtSearch.getText().toString(), maTheLoai);
                } else {
                    getSearchResult(edtSearch.getText().toString(), maTheLoai);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTheLoai = position;
                getSearchResult(edtSearch.getText().toString(), maTheLoai);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getSearchResult(String tenPhim, int maTheLoai) {
        DataClient dataClient = APIUtils.getData();
        Call<List<Phim>> call = dataClient.searchPhim(tenPhim, maTheLoai);
        call.enqueue(new Callback<List<Phim>>() {
            @Override
            public void onResponse(Call<List<Phim>> call, Response<List<Phim>> response) {
                if(phimList != null) {
                    phimList.clear();
                }
                phimAdapter.clear();
                txtNotFound.setText("");
                for(int i = 0; i < response.body().size(); i++) {
                    phimAdapter.add(response.body().get(i));
                    phimList.add(response.body().get(i));
                }
                Log.e("QQQ", response.body().size()+" - " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Phim>> call, Throwable t) {
                Log.e("QQQ", t.getMessage());
                if(!edtSearch.getText().toString().equals("")) {
                    phimAdapter.clear();
                    txtNotFound.setText("Không tìm thấy kết quả nào phù hợp với từ khóa của bạn.");
                }
            }
        });
    }

    private void setupSpinnerData() {
        theLoaiList.add("Tất cả");
        theLoaiList.add("Kinh dị");
        theLoaiList.add("Thần thoại");
        theLoaiList.add("Hành động");
        theLoaiList.add("Hài");
        theLoaiList.add("Viễn tưởng");
        theLoaiList.add("Lịch sử");
        theLoaiList.add("Âm nhạc");
        theLoaiList.add("Phiêu lưu");
        theLoaiList.add("Tình cảm");

        ArrayAdapter<String> adapterSpTheLoai = new ArrayAdapter(this, R.layout.spinner_item,theLoaiList);
        adapterSpTheLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTheLoai.setAdapter(adapterSpTheLoai);
    }

    public void addToolbar() {
        ImageView imgSearch, imgPost, imgHome;
        TextView txtPhim, txtBaiViet;
        final LoginDialog loginDialog = new LoginDialog(PhimActivity.this);

        txtPhim = findViewById(R.id.txtPhimToolbar);
        txtBaiViet = findViewById(R.id.txtBaiVetToolbar);
        imgLogin = findViewById(R.id.imgLoginToolbar);
        imgHome = findViewById(R.id.imgHomeToolbar);
        imgSearch = findViewById(R.id.imgSearchToolbar);
        imgPost = findViewById(R.id.imgDangBaiToolbar);

        txtPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                startActivity(new Intent(getApplicationContext(), BaiVietActivity.class));
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
                edtSearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
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
    protected void onStart() {
        super.onStart();
        addControls();
        addEvents();
        getSearchResult(edtSearch.getText().toString(), maTheLoai);
        setupSpinnerData();
        addToolbar();
    }
}
