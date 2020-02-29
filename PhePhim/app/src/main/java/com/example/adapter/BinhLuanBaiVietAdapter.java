package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.BinhLuanBaiViet;
import com.example.phephim.R;
import com.squareup.picasso.Picasso;

public class BinhLuanBaiVietAdapter extends ArrayAdapter<BinhLuanBaiViet> {
    Activity context;
    int resource;
    TextView txtUser, txtNgay, txtNoiDung, txtDiem;
    ImageView imgUser, imgUp, imgDown;

    public BinhLuanBaiVietAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
         this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = this.context.getLayoutInflater().inflate(this.resource, null);

        txtUser = mView.findViewById(R.id.txtUserBLBV);
        txtDiem = mView.findViewById(R.id.txtDiemBLBV);
        txtNgay = mView.findViewById(R.id.txtNgayBLBV);
        txtNoiDung = mView.findViewById(R.id.txtBLBV);
        imgUser = mView.findViewById(R.id.imgUserBLBV);
        imgUp = mView.findViewById(R.id.imgUpVoteBLBV);
        imgDown = mView.findViewById(R.id.imgDownVoteBLBV);

        BinhLuanBaiViet item = getItem(position);

        txtUser.setText(item.getUser());
        txtNoiDung.setText(item.getNoiDung());
        txtNgay.setText(item.getNgay());
        txtDiem.setText(item.getDiemBL()+"");
        Picasso.get()
                .load(item.getAvatar())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(imgUser);

        return mView;
    }
}
