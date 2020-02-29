package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.model.BinhLuanPhim;
import com.example.phephim.R;

public class DanhGiaPhimAdapter extends ArrayAdapter<BinhLuanPhim> {

    Activity context;
    int resource;

    TextView txtTenPhim, txtDiem, txtNgay, txtNoiDung;

    public DanhGiaPhimAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = this.context.getLayoutInflater().inflate(this.resource, null);

        txtDiem = mView.findViewById(R.id.txtDiemDGPI);
        txtNgay = mView.findViewById(R.id.txtNgayDGPI);
        txtNoiDung = mView.findViewById(R.id.txtNoiDungDGPI);
        txtTenPhim = mView.findViewById(R.id.txtTenDGPI);

        BinhLuanPhim binhLuanPhim = getItem(position);

        txtTenPhim.setText(binhLuanPhim.getMaPhim());
        txtNoiDung.setText(binhLuanPhim.getNoiDung());
        txtNgay.setText(binhLuanPhim.getNgay());
        txtDiem.setText(binhLuanPhim.getDiemPhim() + "");

        return mView;
    }
}