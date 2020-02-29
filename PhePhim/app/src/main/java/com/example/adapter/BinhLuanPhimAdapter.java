package com.example.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.model.BinhLuanPhim;
import com.example.phephim.R;
import com.squareup.picasso.Picasso;

public class BinhLuanPhimAdapter extends ArrayAdapter<BinhLuanPhim> {
    Activity context;
    int resource;

    TextView txtUser, txtDate, txtDanhGiaPhim, txtBinhLuanPhim, txtDiemBLP;
    ImageView imgUser, imgUpVote, imgDownVote;

    public BinhLuanPhimAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        View mView = this.context.getLayoutInflater().inflate(this.resource, null);

        txtUser = mView.findViewById(R.id.txtUserBLP);
        txtDate = mView.findViewById(R.id.txtNgayBLP);
        txtDanhGiaPhim = mView.findViewById(R.id.txtDiemPhimBLP);
        txtBinhLuanPhim = mView.findViewById(R.id.txtNoiDungBinhLuaBLP);
        txtDiemBLP = mView.findViewById(R.id.txtDiemBLP);
//        imgDownVote = mView.findViewById(R.id.imgDownVoteBLP);
//        imgUpVote = mView.findViewById(R.id.imgUpVoteBLP);
        imgUser = mView.findViewById(R.id.imgUserBLP);

        BinhLuanPhim binhLuanPhim = getItem(position);

        txtUser.setText(binhLuanPhim.getUser());
        txtDate.setText(binhLuanPhim.getNgay());
        txtDanhGiaPhim.setText(binhLuanPhim.getDiemPhim() + "");
        txtBinhLuanPhim.setText(binhLuanPhim.getNoiDung());
        txtDiemBLP.setText(binhLuanPhim.getDiemBL() + "");
        Picasso.get()
                .load(binhLuanPhim.getAvatar())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(imgUser);
        return mView;
    }
}
