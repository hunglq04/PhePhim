package com.example.util;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.User;
import com.example.phephim.LoginActivity;
import com.example.phephim.R;
import com.example.service.APIUtils;
import com.example.service.DataClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaiKhoanFragment extends Fragment {

    private View mRootView;
    ImageView imgAvatar, imgEdit;
    TextView txtName;
    Button btnLogout;
    EditNameDialog editNameDialog;
    final int Request_Code_Image = 123;
    String realPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_taikhoan, container, false);

        addControls();
        setUserData();
        addEvents();

        return mRootView;
    }

    private void addControls() {
        imgAvatar = mRootView.findViewById(R.id.imgAvatarFTK);
        imgEdit = mRootView.findViewById(R.id.imgEditNameFTK);
        txtName = mRootView.findViewById(R.id.txtUserFTK);
        btnLogout = mRootView.findViewById(R.id.btnLogoutFTK);
        editNameDialog = new EditNameDialog(getActivity());
    }

    private void setUserData() {
        Picasso.get()
                .load(LoginActivity.user.getAvatar())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(imgAvatar);

        txtName.setText(LoginActivity.user.getTen());
    }

    private void addEvents() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.user = null;
                getActivity().finish();
            }
        });

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Request_Code_Image);
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNameDialog.show();
            }
        });

        editNameDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DataClient dataClient = APIUtils.getData();
                Call<List<User>> call = dataClient.checkLogin(LoginActivity.user.getEmail(), LoginActivity.user.getPassword());
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        LoginActivity.user = response.body().get(0);
                        txtName.setText(LoginActivity.user.getTen());
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if(requestCode == Request_Code_Image && resultCode == getActivity().RESULT_OK && data!= null){
            Uri uri = data.getData();
            realPath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            uploadImage();
        }
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
                        updateImage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("QQQ", t.getMessage());
            }
        });
    }

    private void updateImage(String message) {
        DataClient dataClient1 = APIUtils.getData();
        Call<String> call1 = dataClient1.updateUserImage(LoginActivity.user.getEmail(),
                APIUtils.Base_Url + "images/" + message);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(mRootView.getContext(), "Cập nhật ảnh thành công.", Toast.LENGTH_LONG).show();
                getNewImage();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getNewImage() {
        DataClient dataClient123123 = APIUtils.getData();
        Call<List<User>> call123123 = dataClient123123.checkLogin(LoginActivity.user.getEmail(), LoginActivity.user.getPassword());
        call123123.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                LoginActivity.user = response.body().get(0);
                Picasso.get()
                        .load(LoginActivity.user.getAvatar())
                        .placeholder(android.R.drawable.ic_menu_report_image)
                        .error(android.R.drawable.ic_menu_report_image)
                        .into(imgAvatar);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
