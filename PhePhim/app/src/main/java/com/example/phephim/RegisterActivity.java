package com.example.phephim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.service.APIUtils;
import com.example.service.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtEmail, edtPass, edtUsername;
    Button btnRegister;
    String email, password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmail = findViewById(R.id.edtEmailREG);
        edtPass = findViewById(R.id.edtPasswordREG);
        edtUsername = findViewById(R.id.edtNameAccountREG);
        btnRegister = findViewById(R.id.btnRegisterREG);
    }

    private void addEvents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUsername.getText().toString().equals("")
                        || edtEmail.getText().toString().equals("")
                        || edtPass.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Bạn hãy nhập đầy đủ thông tin!",
                            Toast.LENGTH_LONG).show();
                    return;
                } else {
                    email = edtEmail.getText().toString();
                    name = edtUsername.getText().toString();
                    password = edtPass.getText().toString();
                    if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {
                        edtEmail.setError("Email không hợp lệ");
                        Toast.makeText(getApplicationContext(), "Email không hợp lệ",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        register(email, name, password);
                    }
                }
            }
        });
    }

    private void register(String mail, String username, String pass) {
        DataClient registerAccount = APIUtils.getData();
        Call<String> callback = registerAccount.registerAccount(mail, username, pass);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (result.equals("Success")) {
                    Toast.makeText(getApplicationContext(), "Đăng kí thành công",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    edtEmail.setError("Email đã được sử dụng");
                    Toast.makeText(getApplicationContext(), "Email đã được sử dụng.",
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
