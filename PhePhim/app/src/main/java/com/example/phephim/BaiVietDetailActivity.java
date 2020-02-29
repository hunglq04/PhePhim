package com.example.phephim;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.BinhLuanBaiVietAdapter;
import com.example.model.BaiViet;
import com.example.model.BinhLuanBaiViet;
import com.example.service.APIUtils;
import com.example.service.DataClient;
import com.example.util.LoginDialog;
import com.example.util.MyListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiVietDetailActivity extends AppCompatActivity {

    Spinner spinnerSapXep;
    MyListView lvBinhLuan;
    BinhLuanBaiVietAdapter binhLuanAdapter;
    TextView txtTieuDe, txtTacGia, txtNgay, txtNoiDung, txtDiem, txtSoBinhLuan, txtNoComment;
    ImageView imgTacGia, imgAnh, imgLogin, imgLuuBaiViet;
    EditText edtBinhLuan;
    Button btnTheLoai, btnGui;
    BaiViet baiViet;
    LoginDialog loginDialog;
    boolean isLuuBV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_viet_detail);

        addControl();
        setData();
        setupSpinner();
        setBinhLuan(0);
        checkLuuBV();
        addToolbar();
        addEvent();
    }

    private void checkLuuBV() {
        if(LoginActivity.user == null){
            imgLuuBaiViet.setImageResource(R.drawable.bookmark);
            return;
        }
        DataClient checkLuu = APIUtils.getData();
        Call<String> callCheck = checkLuu.checkLuuBaiViet(baiViet.getId(), LoginActivity.user.getEmail());
        callCheck.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("1")){
                    isLuuBV = true;
                    imgLuuBaiViet.setImageResource(R.drawable.bookmarkchecke);
                } else if (response.body().equals("0")){
                    isLuuBV = false;
                } else {
                    Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_LONG).show();
                    imgLuuBaiViet.setImageResource(R.drawable.bookmark);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void setBinhLuan(int sapXep) {
        DataClient dataClient = APIUtils.getData();
        Call<List<BinhLuanBaiViet>> call = dataClient.getBinhLuanBaiViet(baiViet.getId(), sapXep);
        call.enqueue(new Callback<List<BinhLuanBaiViet>>() {
            @Override
            public void onResponse(Call<List<BinhLuanBaiViet>> call, Response<List<BinhLuanBaiViet>> response) {
                binhLuanAdapter.clear();
                if (response.body().size() == 0){
                    txtNoComment.setText("Hiện chưa có bình luận nào.");
                    txtSoBinhLuan.setText("Bình luận");
                }
                for(int i = 0; i < response.body().size(); i++) {
                    binhLuanAdapter.add(response.body().get(i));
                    txtNoComment.setText("");
                    txtSoBinhLuan.setText(response.body().size() + " Bình luận");
                }
            }

            @Override
            public void onFailure(Call<List<BinhLuanBaiViet>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addControl() {
        lvBinhLuan = findViewById(R.id.lvBinhLuanBV);
        binhLuanAdapter = new BinhLuanBaiVietAdapter(BaiVietDetailActivity.this, R.layout.binh_luan_bai_viet_item);
        lvBinhLuan.setAdapter(binhLuanAdapter);

        txtTieuDe = findViewById(R.id.txtTieuDeBVDetail);
        txtTacGia = findViewById(R.id.txtTacGiaBV);
        txtNgay = findViewById(R.id.txtNgayDangBV);
        txtNoiDung = findViewById(R.id.txtNoiDungBV);
        txtDiem = findViewById(R.id.txtDiemBaiVietDetail);
        txtSoBinhLuan = findViewById(R.id.txtSoBLBV);

        imgTacGia = findViewById(R.id.imgTacGiaBV);
        imgAnh = findViewById(R.id.imgAnhBV);
        imgLuuBaiViet = findViewById(R.id.imgLuuBaiViet);

        txtNoComment = findViewById(R.id.txtNoCommentBV);

        btnTheLoai = findViewById(R.id.btnLoaiBV);
        btnGui = findViewById(R.id.btnGuiBLBaiViet);
        edtBinhLuan = findViewById(R.id.edtBinhLuanBV);

        loginDialog = new LoginDialog(this);

    }

    private void setData() {
        Intent intent = getIntent();
        baiViet = (BaiViet) intent.getSerializableExtra("BaiViet");

        txtTieuDe.setText(baiViet.getTieuDe());
        txtTacGia.setText(baiViet.getTacGia());
        txtNgay.setText(baiViet.getNgay());
        txtNoiDung.setText(baiViet.getNoiDung());
        txtDiem.setText(baiViet.getDiem() + "");
        txtSoBinhLuan.setText("3 Bình luận");
        btnTheLoai.setText(baiViet.getTheLoai());

        Picasso.get()
                .load(baiViet.getAvatar())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(imgTacGia);
        if(baiViet.getAnh() != null) {
            Picasso.get()
                    .load(baiViet.getAnh())
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgAnh);
        }
    }

    private void setupSpinner() {
        spinnerSapXep = findViewById(R.id.spinnerBLBV);

        List<String> list = new ArrayList<>();
        list.add("Hay nhất");
        list.add("Mới nhất");

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter(this, R.layout.spinner_item,list);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSapXep.setAdapter(adapterSpinner);

        spinnerSapXep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setBinhLuan(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void guiBinhLuanBaiViet(View view) {
        if(LoginActivity.user == null) {
            Toast.makeText(getApplicationContext(), "Bạn cần đăng nhập để gửi bình luận.", Toast.LENGTH_LONG).show();
            loginDialog.show();
            return;
        }
        String maBaiViet = baiViet.getId();
        String email = LoginActivity.user.getEmail();
        String noiDung = edtBinhLuan.getText().toString();
        if (noiDung.equals("")){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập bình luận!", Toast.LENGTH_LONG).show();
            return;
        }
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.insertBinhLuanBaiViet(maBaiViet, email, noiDung);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("Success")){
                    setBinhLuan(1);
                    edtBinhLuan.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void addToolbar() {
        ImageView imgSearch, imgPost, imgHome;
        TextView txtPhim, txtBaiViet;
        final LoginDialog loginDialog = new LoginDialog(BaiVietDetailActivity.this);

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

    private void addEvent() {
        loginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(LoginActivity.user != null){
                    Picasso.get()
                            .load(LoginActivity.user.getAvatar().toString())
                            .placeholder(android.R.drawable.ic_menu_report_image)
                            .error(android.R.drawable.ic_menu_report_image)
                            .into(imgLogin);
                }
                else {
                    imgLogin.setImageResource(R.drawable.login);
                }
            }
        });

        imgLuuBaiViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginActivity.user == null){
                    Toast.makeText(getApplicationContext(), "Bạn cần đăng nhập để lưu bài viết!",
                            Toast.LENGTH_LONG).show();
                    loginDialog.show();
                    return;
                }
                if(!isLuuBV){
                    DataClient luuBV = APIUtils.getData();
                    Call<String> callLuuBV = luuBV.insertLuuBaiViet(baiViet.getId(), LoginActivity.user.getEmail());
                    callLuuBV.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().equals("Success")) {
                                imgLuuBaiViet.setImageResource(R.drawable.bookmarkchecke);
                                checkLuuBV();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                } else {
                    DataClient delete = APIUtils.getData();
                    Call<String> callDelete = delete.deleteLuuBaiViet(baiViet.getId(), LoginActivity.user.getEmail());
                    callDelete.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().equals("Success")){
                                imgLuuBaiViet.setImageResource(R.drawable.bookmark);
                                //checkLuuBV();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        addControl();
        setData();
        setupSpinner();
        setBinhLuan(0);
        checkLuuBV();
        addToolbar();
        addEvent();
    }
}
