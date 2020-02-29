package com.example.phephim;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.BinhLuanPhimAdapter;
import com.example.model.BinhLuanPhim;
import com.example.model.Phim;
import com.example.model.User;
import com.example.service.APIUtils;
import com.example.service.DataClient;
import com.example.util.LoginDialog;
import com.example.util.MyListView;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhimDetailActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgBanner, imgLogin, imgPoster, imgStar1, imgStar2, imgStar3, imgStar4, imgStar5, imgStar6, imgStar7, imgStar8, imgStar9, imgStar10;
    TextView txtDiem, txtTieuDe, txtNoiDung, txtTheLoai, txtDaoDien, txtDienVien, txtNgay, txtThoiLuong, txtNoComment;
    EditText edtBinhLuan;
    ProgressBar pbPhimDetail;
    Button btnGuiBinhLuan, btnHuy, btnXoa;

    Spinner spinnerSapXep;
    MyListView lvBinhLuan;
    BinhLuanPhimAdapter adapterBL;
    Phim phim;
    int diemPhim = -1;
    LoginDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phim_detail);

        addControls();
        setPhimData();
        setBinhLuanData(0);
        setupSpinnerData();
        addToolbar();
        addEvents();
    }

    private void addControls() {
        imgBanner = findViewById(R.id.imgBannerPhimDetail);
        imgPoster = findViewById(R.id.imgPosterPhimDetail);
        txtDiem = findViewById(R.id.txtDiemPhimDetail);
        txtTieuDe = findViewById(R.id.txtTenPhimDetail);
        txtNoiDung = findViewById(R.id.txtNoiDungPhimDetail);
        txtTheLoai = findViewById(R.id.txtTheLoaiPhimDetail);
        txtDaoDien = findViewById(R.id.txtDaoDienPhimDetail);
        txtDienVien = findViewById(R.id.txtDienVienPhimDetail);
        txtNgay = findViewById(R.id.txtNgayKhoiChieuDetail);
        txtThoiLuong = findViewById(R.id.txtThoiLuongPhimDetail);
        lvBinhLuan = findViewById(R.id.lvBinhLuanPhimDetail);
        adapterBL = new BinhLuanPhimAdapter(PhimDetailActivity.this, R.layout.binh_luan_phim_item);
        lvBinhLuan.setAdapter(adapterBL);
        txtNoComment = findViewById(R.id.txtNoCommentPhim);
        edtBinhLuan = findViewById(R.id.edtBinhLuanPhim);
        pbPhimDetail = findViewById(R.id.pbPhimDetail);

        imgStar1 = findViewById(R.id.imgStar1);
        imgStar2 = findViewById(R.id.imgStar2);
        imgStar3 = findViewById(R.id.imgStar3);
        imgStar4 = findViewById(R.id.imgStar4);
        imgStar5 = findViewById(R.id.imgStar5);
        imgStar6 = findViewById(R.id.imgStar6);
        imgStar7 = findViewById(R.id.imgStar7);
        imgStar8 = findViewById(R.id.imgStar8);
        imgStar9 = findViewById(R.id.imgStar9);
        imgStar10 = findViewById(R.id.imgStar10);

        btnGuiBinhLuan = findViewById(R.id.btnGuiBLDanhGiaPhim);
        btnHuy = findViewById(R.id.btnHuyPD);
        btnHuy.setVisibility(View.GONE);
        btnXoa = findViewById(R.id.btnXoaPD);
        btnXoa.setVisibility(View.GONE);
        loginDialog = new LoginDialog(this);
    }

    private void setPhimData() {
        Intent intent = getIntent();
        phim = (Phim) intent.getSerializableExtra("Phim");

        Picasso.get()
                .load(phim.getPoster())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(imgPoster);
        Picasso.get()
                .load(phim.getBanner())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(imgBanner);
        txtDiem.setText(phim.getDiem()+"");
        txtTieuDe.setText(phim.getTen());
        txtNoiDung.setText(phim.getMoTa());
        txtTheLoai.setText(phim.getTheLoai());
        txtDaoDien.setText(phim.getDaoDien());
        txtDienVien.setText(phim.getDienVien());
        txtNgay.setText(phim.getNgay());
        txtThoiLuong.setText(phim.getThoiLuong());
        pbPhimDetail.setProgress((int) (phim.getDiem() * 10));
    }

    private void setBinhLuanData(int sapXep) {
        DataClient dataClient = APIUtils.getData();
        Call<List<BinhLuanPhim>> call = dataClient.getBinhLuanPhim(phim.getId(), sapXep);
        call.enqueue(new Callback<List<BinhLuanPhim>>() {
            @Override
            public void onResponse(Call<List<BinhLuanPhim>> call, Response<List<BinhLuanPhim>> response) {
                if (response.body().size() == 0) {
                    txtNoComment.setText("Hiện chưa có đánh giá nào.");
                } else {
                    adapterBL.clear();
                    for(int i = 0; i < response.body().size(); i++) {
                        txtNoComment.setText("");
                        adapterBL.add(response.body().get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BinhLuanPhim>> call, Throwable t) {

            }
        });
    }

    private void setupSpinnerData() {
        spinnerSapXep = findViewById(R.id.spnPhimDetail);

        List<String> list = new ArrayList<>();
        list.add("Hay nhất");
        list.add("Mới nhất");

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter(this, R.layout.spinner_item,list);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSapXep.setAdapter(adapterSpinner);

        spinnerSapXep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setBinhLuanData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void guiBinhLuanPhim() {

        if(LoginActivity.user == null) {
            Toast.makeText(getApplicationContext(), "Bạn cần đăng nhập để gửi đánh giá.", Toast.LENGTH_LONG).show();
            loginDialog.show();
            return;
        }

        if (diemPhim < 0) {
            Toast.makeText(getApplicationContext(), "Bạn chưa chọn điểm đánh giá",
                    Toast.LENGTH_LONG).show();
            return;
        }
        String noiDungBL = edtBinhLuan.getText().toString();
        String email = LoginActivity.user.getEmail();

        if (noiDungBL.equals("")) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập nội dung bình luận!",
                    Toast.LENGTH_LONG).show();
        } else {
            DataClient dataClient = APIUtils.getData();
            Call<String> call = dataClient.insertBinhLuanPhim(
                    phim.getId(),
                    email,
                    noiDungBL,
                    diemPhim);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().equals("Success")) {
                        spinnerSapXep.setSelection(1);
                        btnGuiBinhLuan.setText("   Sửa đánh giá   ");
                        DataClient dataClient1 = APIUtils.getData();
                        Call<List<Phim>> call1 = dataClient1.getPhimHot(phim.getTen());
                        call1.enqueue(new Callback<List<Phim>>() {
                            @Override
                            public void onResponse(Call<List<Phim>> call, Response<List<Phim>> response) {
                                phim = response.body().get(0);
                                txtDiem.setText(phim.getDiem()+"");
                            }

                            @Override
                            public void onFailure(Call<List<Phim>> call, Throwable t) {

                            }
                        });
                        edtBinhLuan.setVisibility(View.GONE);
                        lvBinhLuan.requestFocus();
                    } else {
                        Toast.makeText(getApplicationContext(), "Đã có lỗi xãy ra!",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("QQQ", t.getMessage());
                }
            });
        }
    }

    private void setBinhLuanOfUser(User user, String maPhim) {
        if(user != null) {
            DataClient dataClient = APIUtils.getData();
            Call<List<BinhLuanPhim>> call = dataClient.getBinhLuanPhimOfUser(user.getEmail(), maPhim);
            call.enqueue(new Callback<List<BinhLuanPhim>>() {
                @Override
                public void onResponse(Call<List<BinhLuanPhim>> call, Response<List<BinhLuanPhim>> response) {
                    if(response.body().size() == 0) {
                        return;
                    }
                    edtBinhLuan.setVisibility(View.GONE);
                    edtBinhLuan.setText(response.body().get(0).getNoiDung());
                    btnGuiBinhLuan.setText("   Sửa đánh giá   ");
                    int diemDanhGia = response.body().get(0).getDiemPhim();
                    switch (diemDanhGia) {
                        case 1:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.darkstar);
                            imgStar3.setImageResource(R.drawable.darkstar);
                            imgStar4.setImageResource(R.drawable.darkstar);
                            imgStar5.setImageResource(R.drawable.darkstar);
                            imgStar6.setImageResource(R.drawable.darkstar);
                            imgStar7.setImageResource(R.drawable.darkstar);
                            imgStar8.setImageResource(R.drawable.darkstar);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 2:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.darkstar);
                            imgStar4.setImageResource(R.drawable.darkstar);
                            imgStar5.setImageResource(R.drawable.darkstar);
                            imgStar6.setImageResource(R.drawable.darkstar);
                            imgStar7.setImageResource(R.drawable.darkstar);
                            imgStar8.setImageResource(R.drawable.darkstar);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 3:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.yellowstart);
                            imgStar4.setImageResource(R.drawable.darkstar);
                            imgStar5.setImageResource(R.drawable.darkstar);
                            imgStar6.setImageResource(R.drawable.darkstar);
                            imgStar7.setImageResource(R.drawable.darkstar);
                            imgStar8.setImageResource(R.drawable.darkstar);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 4:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.yellowstart);
                            imgStar4.setImageResource(R.drawable.yellowstart);
                            imgStar5.setImageResource(R.drawable.darkstar);
                            imgStar6.setImageResource(R.drawable.darkstar);
                            imgStar7.setImageResource(R.drawable.darkstar);
                            imgStar8.setImageResource(R.drawable.darkstar);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 5:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.yellowstart);
                            imgStar4.setImageResource(R.drawable.yellowstart);
                            imgStar5.setImageResource(R.drawable.yellowstart);
                            imgStar6.setImageResource(R.drawable.darkstar);
                            imgStar7.setImageResource(R.drawable.darkstar);
                            imgStar8.setImageResource(R.drawable.darkstar);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 6:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.yellowstart);
                            imgStar4.setImageResource(R.drawable.yellowstart);
                            imgStar5.setImageResource(R.drawable.yellowstart);
                            imgStar6.setImageResource(R.drawable.yellowstart);
                            imgStar7.setImageResource(R.drawable.darkstar);
                            imgStar8.setImageResource(R.drawable.darkstar);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 7:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.yellowstart);
                            imgStar4.setImageResource(R.drawable.yellowstart);
                            imgStar5.setImageResource(R.drawable.yellowstart);
                            imgStar6.setImageResource(R.drawable.yellowstart);
                            imgStar7.setImageResource(R.drawable.yellowstart);
                            imgStar8.setImageResource(R.drawable.darkstar);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 8:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.yellowstart);
                            imgStar4.setImageResource(R.drawable.yellowstart);
                            imgStar5.setImageResource(R.drawable.yellowstart);
                            imgStar6.setImageResource(R.drawable.yellowstart);
                            imgStar7.setImageResource(R.drawable.yellowstart);
                            imgStar8.setImageResource(R.drawable.yellowstart);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 9:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.yellowstart);
                            imgStar4.setImageResource(R.drawable.yellowstart);
                            imgStar5.setImageResource(R.drawable.yellowstart);
                            imgStar6.setImageResource(R.drawable.yellowstart);
                            imgStar7.setImageResource(R.drawable.yellowstart);
                            imgStar8.setImageResource(R.drawable.yellowstart);
                            imgStar9.setImageResource(R.drawable.yellowstart);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            break;
                        case 10:
                            imgStar1.setImageResource(R.drawable.yellowstart);
                            imgStar2.setImageResource(R.drawable.yellowstart);
                            imgStar3.setImageResource(R.drawable.yellowstart);
                            imgStar4.setImageResource(R.drawable.yellowstart);
                            imgStar5.setImageResource(R.drawable.yellowstart);
                            imgStar6.setImageResource(R.drawable.yellowstart);
                            imgStar7.setImageResource(R.drawable.yellowstart);
                            imgStar8.setImageResource(R.drawable.yellowstart);
                            imgStar9.setImageResource(R.drawable.yellowstart);
                            imgStar10.setImageResource(R.drawable.yellowstart);
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<BinhLuanPhim>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void addEvents(){
        imgStar1.setOnClickListener(this);
        imgStar2.setOnClickListener(this);
        imgStar3.setOnClickListener(this);
        imgStar4.setOnClickListener(this);
        imgStar5.setOnClickListener(this);
        imgStar6.setOnClickListener(this);
        imgStar7.setOnClickListener(this);
        imgStar8.setOnClickListener(this);
        imgStar9.setOnClickListener(this);
        imgStar10.setOnClickListener(this);

        btnGuiBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnGuiBinhLuan.getText().equals("Gửi")){
                    guiBinhLuanPhim();
                } else if(btnGuiBinhLuan.getText().equals("Lưu")){
                    DataClient updateBinhLuan = APIUtils.getData();
                    Call<String> callLuu =
                            updateBinhLuan.updateBinhLuanPhimOfUser(
                                    LoginActivity.user.getEmail(),
                                    phim.getId(),
                                    edtBinhLuan.getText().toString()
                                    );
                    callLuu.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().equals("Success")){
                                Toast.makeText(getApplicationContext(),"Cập nhật thành công!",
                                        Toast.LENGTH_LONG).show();
                                btnXoa.setVisibility(View.GONE);
                                btnHuy.setVisibility(View.GONE);
                                edtBinhLuan.setVisibility(View.GONE);
                                btnGuiBinhLuan.setText("   Sửa đánh giá   ");
                                setBinhLuanData(1);
                            } else {
                                Toast.makeText(getApplicationContext(), "Có lỗi xảy ra, không thể cập nhật!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                } else {
                    edtBinhLuan.setVisibility(View.VISIBLE);
                    btnHuy.setVisibility(View.VISIBLE);
                    btnXoa.setVisibility(View.VISIBLE);
                    btnGuiBinhLuan.setText("Lưu");
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnXoa.setVisibility(View.GONE);
                btnHuy.setVisibility(View.GONE);
                edtBinhLuan.setVisibility(View.GONE);
                btnGuiBinhLuan.setText("   Sửa đánh giá   ");
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataClient deleteBL = APIUtils.getData();
                Call<String> callDeleteBL = deleteBL.deleteBinhLuanPhim(LoginActivity.user.getEmail(), phim.getId());
                callDeleteBL.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equals("Success")){
                            Toast.makeText(getApplicationContext(), "Xóa thành công",
                                    Toast.LENGTH_LONG).show();
                            btnXoa.setVisibility(View.GONE);
                            btnHuy.setVisibility(View.GONE);
                            edtBinhLuan.setText("");
                            btnGuiBinhLuan.setText("Gửi");
                            edtBinhLuan.setVisibility(View.VISIBLE);
                            imgStar1.setImageResource(R.drawable.darkstar);
                            imgStar2.setImageResource(R.drawable.darkstar);
                            imgStar3.setImageResource(R.drawable.darkstar);
                            imgStar4.setImageResource(R.drawable.darkstar);
                            imgStar5.setImageResource(R.drawable.darkstar);
                            imgStar6.setImageResource(R.drawable.darkstar);
                            imgStar7.setImageResource(R.drawable.darkstar);
                            imgStar8.setImageResource(R.drawable.darkstar);
                            imgStar9.setImageResource(R.drawable.darkstar);
                            imgStar10.setImageResource(R.drawable.darkstar);
                            setBinhLuanData(0);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Xóa thất bại, Có lỗi xảy ra!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        loginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loadData();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.equals(imgStar1)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.darkstar);
            imgStar3.setImageResource(R.drawable.darkstar);
            imgStar4.setImageResource(R.drawable.darkstar);
            imgStar5.setImageResource(R.drawable.darkstar);
            imgStar6.setImageResource(R.drawable.darkstar);
            imgStar7.setImageResource(R.drawable.darkstar);
            imgStar8.setImageResource(R.drawable.darkstar);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 1;
        }
        if (view.equals(imgStar2)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.darkstar);
            imgStar4.setImageResource(R.drawable.darkstar);
            imgStar5.setImageResource(R.drawable.darkstar);
            imgStar6.setImageResource(R.drawable.darkstar);
            imgStar7.setImageResource(R.drawable.darkstar);
            imgStar8.setImageResource(R.drawable.darkstar);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 2;
        }
        if (view.equals(imgStar3)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.yellowstart);
            imgStar4.setImageResource(R.drawable.darkstar);
            imgStar5.setImageResource(R.drawable.darkstar);
            imgStar6.setImageResource(R.drawable.darkstar);
            imgStar7.setImageResource(R.drawable.darkstar);
            imgStar8.setImageResource(R.drawable.darkstar);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 3;
        }
        if (view.equals(imgStar4)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.yellowstart);
            imgStar4.setImageResource(R.drawable.yellowstart);
            imgStar5.setImageResource(R.drawable.darkstar);
            imgStar6.setImageResource(R.drawable.darkstar);
            imgStar7.setImageResource(R.drawable.darkstar);
            imgStar8.setImageResource(R.drawable.darkstar);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 4;
        }
        if (view.equals(imgStar5)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.yellowstart);
            imgStar4.setImageResource(R.drawable.yellowstart);
            imgStar5.setImageResource(R.drawable.yellowstart);
            imgStar6.setImageResource(R.drawable.darkstar);
            imgStar7.setImageResource(R.drawable.darkstar);
            imgStar8.setImageResource(R.drawable.darkstar);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 5;
        }
        if (view.equals(imgStar6)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.yellowstart);
            imgStar4.setImageResource(R.drawable.yellowstart);
            imgStar5.setImageResource(R.drawable.yellowstart);
            imgStar6.setImageResource(R.drawable.yellowstart);
            imgStar7.setImageResource(R.drawable.darkstar);
            imgStar8.setImageResource(R.drawable.darkstar);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 6;
        }
        if (view.equals(imgStar7)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.yellowstart);
            imgStar4.setImageResource(R.drawable.yellowstart);
            imgStar5.setImageResource(R.drawable.yellowstart);
            imgStar6.setImageResource(R.drawable.yellowstart);
            imgStar7.setImageResource(R.drawable.yellowstart);
            imgStar8.setImageResource(R.drawable.darkstar);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 7;
        }
        if (view.equals(imgStar8)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.yellowstart);
            imgStar4.setImageResource(R.drawable.yellowstart);
            imgStar5.setImageResource(R.drawable.yellowstart);
            imgStar6.setImageResource(R.drawable.yellowstart);
            imgStar7.setImageResource(R.drawable.yellowstart);
            imgStar8.setImageResource(R.drawable.yellowstart);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 8;
        }
        if (view.equals(imgStar9)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.yellowstart);
            imgStar4.setImageResource(R.drawable.yellowstart);
            imgStar5.setImageResource(R.drawable.yellowstart);
            imgStar6.setImageResource(R.drawable.yellowstart);
            imgStar7.setImageResource(R.drawable.yellowstart);
            imgStar8.setImageResource(R.drawable.yellowstart);
            imgStar9.setImageResource(R.drawable.yellowstart);
            imgStar10.setImageResource(R.drawable.darkstar);

            diemPhim = 9;
        }
        if (view.equals(imgStar10)) {
            imgStar1.setImageResource(R.drawable.yellowstart);
            imgStar2.setImageResource(R.drawable.yellowstart);
            imgStar3.setImageResource(R.drawable.yellowstart);
            imgStar4.setImageResource(R.drawable.yellowstart);
            imgStar5.setImageResource(R.drawable.yellowstart);
            imgStar6.setImageResource(R.drawable.yellowstart);
            imgStar7.setImageResource(R.drawable.yellowstart);
            imgStar8.setImageResource(R.drawable.yellowstart);
            imgStar9.setImageResource(R.drawable.yellowstart);
            imgStar10.setImageResource(R.drawable.yellowstart);

            diemPhim = 10;
        }
    }

    public void addToolbar() {
        ImageView imgSearch, imgPost, imgHome;
        TextView txtPhim, txtBaiViet;
        final LoginDialog loginDialog = new LoginDialog(PhimDetailActivity.this);

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

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        addToolbar();
    }

    private void loadData() {
        if(LoginActivity.user != null){
            Picasso.get()
                    .load(LoginActivity.user.getAvatar().toString())
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgLogin);
            setBinhLuanOfUser(LoginActivity.user, phim.getId());
        }
        else {
            imgLogin.setImageResource(R.drawable.login);
            edtBinhLuan.setText("");
            btnGuiBinhLuan.setText("Gửi");
            edtBinhLuan.setVisibility(View.VISIBLE);
            imgStar1.setImageResource(R.drawable.darkstar);
            imgStar2.setImageResource(R.drawable.darkstar);
            imgStar3.setImageResource(R.drawable.darkstar);
            imgStar4.setImageResource(R.drawable.darkstar);
            imgStar5.setImageResource(R.drawable.darkstar);
            imgStar6.setImageResource(R.drawable.darkstar);
            imgStar7.setImageResource(R.drawable.darkstar);
            imgStar8.setImageResource(R.drawable.darkstar);
            imgStar9.setImageResource(R.drawable.darkstar);
            imgStar10.setImageResource(R.drawable.darkstar);
        }
    }
}
