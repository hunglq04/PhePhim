package com.example.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phephim.LoginActivity;
import com.example.phephim.R;
import com.example.service.APIUtils;
import com.example.service.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNameDialog extends Dialog {

    Activity context;
    EditText edtName;
    Button btnLuu, btnHuy;

    public EditNameDialog(Activity context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_edit_name);
        AddControl();
        addEvent();
    }

    private void AddControl() {
        edtName = findViewById(R.id.edtNameDialog);
        btnLuu = findViewById(R.id.btnLuuDEN);
        btnHuy = findViewById(R.id.btnHuyDEN);

        edtName.setText(LoginActivity.user.getTen());
    }

    private void addEvent() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataClient dataClient = APIUtils.getData();
                Call<String> call = dataClient.updateUserName(LoginActivity.user.getEmail(), edtName.getText().toString());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equals("Success")){
                            dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
