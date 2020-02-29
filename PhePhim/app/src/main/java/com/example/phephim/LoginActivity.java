package com.example.phephim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.User;
import com.example.service.APIUtils;
import com.example.service.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView txtRegister, txtForgotPassword;
    String email;
    String password;
    public static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmail = findViewById(R.id.edtEmailLG);
        edtPassword = findViewById(R.id.edtPasswordLG);
        btnLogin = findViewById(R.id.btnLoginLG);
        txtRegister = findViewById(R.id.txtRegisterLG);
        txtForgotPassword = findViewById(R.id.txtForgotPassLG);
    }

    private void addEvents() {
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
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
                    edtEmail.setError("Bạn chưa nhập email");
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập email!",
                            Toast.LENGTH_LONG).show();
                    edtEmail.requestFocus();
                    return;
                } else if (edtPassword.getText().toString().equals("")){
                    edtPassword.setError("Bạn chưa nhập mật khẩu");
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu!",
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
                    user = users.get(0);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Email hoặc mật khẩu không chính xác!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
