package com.example.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.User;
import com.example.phephim.LoginActivity;
import com.example.phephim.R;
import com.example.phephim.RegisterActivity;
import com.example.service.APIUtils;
import com.example.service.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDialog extends Dialog {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView txtRegister, txtForgotPassword;
    String email;
    String password;

    Activity context;

    public LoginDialog(Activity context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_login);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmail = findViewById(R.id.edtEmailDialog);
        edtPassword = findViewById(R.id.edtPasswordDialog);
        btnLogin = findViewById(R.id.btnLoginDialog);
        txtRegister = findViewById(R.id.txtRegisterDialog);
        txtForgotPassword = findViewById(R.id.txtForgotPassDialog);
    }

    private void addEvents() {
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RegisterActivity.class));
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtEmail.getText().toString().equals("")) {
                    Toast.makeText(context, "Bạn chưa nhập email!",
                            Toast.LENGTH_LONG).show();
                    edtEmail.requestFocus();
                    return;
                } else if (edtPassword.getText().toString().equals("")){
                    Toast.makeText(context, "Bạn chưa nhập mật khẩu!",
                            Toast.LENGTH_LONG).show();
                    edtPassword.requestFocus();
                } else {
                    email = edtEmail.getText().toString();
                    password = edtPassword.getText().toString();
                    checkLogin(email, password);
                }
            }
        });
    }

    private void checkLogin(String mail, String pass) {
        DataClient dataClient = APIUtils.getData();
        Call<List<User>> callback = dataClient.checkLogin(mail, pass);
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> users = (ArrayList<User>) response.body();
                if(users.size() > 0){
                    LoginActivity.user = users.get(0);
                    dismiss();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Email hoặc mật khẩu không chính xác!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
