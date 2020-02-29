package com.example.phephim;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adapter.BaiVietAdapter;
import com.example.model.BaiViet;
import com.example.service.APIUtils;
import com.example.service.DataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangBaiVietActivity extends AppCompatActivity {

    Spinner spTheLoai;
    List<String> theLoaiList;
    EditText edtTieuDe, edtNoiDung;
    ImageView imgAnhBia;
    Button btnDangBai;
    final int Request_Code_Image = 123;
    String realPath;
    boolean isSetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_bai_viet);

        addControl();
        addEvent();
        setupSpinner();
    }

    private void addControl() {
        spTheLoai = findViewById(R.id.spnDanhMucDBV);
        theLoaiList = new ArrayList<>();
        edtNoiDung = findViewById(R.id.edtNoiDungDBV);
        edtTieuDe = findViewById(R.id.edtTieuDeDBV);
        imgAnhBia = findViewById(R.id.imgAnhDBV);
        btnDangBai = findViewById(R.id.btnDangBaiDBV);
    }

    private void addEvent() {
        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNoiDung.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập nội dung bài viết.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(edtTieuDe.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập tiêu đề bài viết.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(spTheLoai.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext(), "Bạn chưa chọn thể loại.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(!isSetImage) {
                   insertBaiViet("");
                }
                else {
                    uploadImage();
                }
            }
        });

        imgAnhBia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Request_Code_Image);
            }
        });
    }

    private void insertBaiViet(String anh) {
        final DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.insertBaiViet(
                LoginActivity.user.getEmail(),
                edtTieuDe.getText().toString(),
                edtNoiDung.getText().toString(),
                spTheLoai.getSelectedItemPosition(),anh
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("Success")){
                    DataClient dataClient1 = APIUtils.getData();
                    Call<List<BaiViet>> callBaiViet = dataClient1.getBaiViet(edtTieuDe.getText().toString());
                    callBaiViet.enqueue(new Callback<List<BaiViet>>() {
                        @Override
                        public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                            Intent intent = new Intent(getApplicationContext(), BaiVietDetailActivity.class);
                            intent.putExtra("BaiViet", response.body().get(0));
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<List<BaiViet>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra!",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void uploadImage() {
        File file = new File(realPath);
        String file_path = file.getAbsolutePath();
        String[] fileNameArray = file_path.split("\\.");

        file_path = fileNameArray[0] + System.currentTimeMillis() + "." + fileNameArray[1];
        final RequestBody requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                file);

        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "uploaded_file",
                file_path,
                requestBody);

        final DataClient dataClient = APIUtils.getData();

        Call<String> callback = dataClient.uploadImage(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response != null){
                    String message = response.body();
                    if (message.length() > 0){
                        insertBaiViet(APIUtils.Base_Url + "images/" + message);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("QQQ", t.getMessage());
            }
        });
    }

    private void setupSpinner() {
        theLoaiList.add("Chọn thể loại");
        theLoaiList.add("Phân tích");
        theLoaiList.add("Hỏi đáp");
        theLoaiList.add("Chia sẻ");

        ArrayAdapter<String> adapterSpTheLoai = new ArrayAdapter(this, R.layout.spinner_item,theLoaiList);
        adapterSpTheLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTheLoai.setAdapter(adapterSpTheLoai);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Request_Code_Image && resultCode == RESULT_OK && data!= null){
            Uri uri = data.getData();
            realPath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhBia.setImageBitmap(bitmap);
                isSetImage = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
