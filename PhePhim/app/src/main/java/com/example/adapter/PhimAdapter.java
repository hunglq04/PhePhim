package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.model.Phim;
import com.example.phephim.R;
import com.example.service.APIUtils;
import com.squareup.picasso.Picasso;

public class PhimAdapter extends ArrayAdapter<Phim> {
    Activity context;
    int resource;

    public PhimAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View mView =this.context.getLayoutInflater().inflate(this.resource, null);

        TextView txtDiem = mView.findViewById(R.id.txtDiemPhimMain);
        ImageView imgPoster = mView.findViewById(R.id.imgPosterMain);
        ProgressBar progressBar = mView.findViewById(R.id.pbMain);

        Phim phim = getItem(position);

        txtDiem.setText(phim.getDiem()+"");
        Picasso.get()
                .load(phim.getPoster())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(imgPoster);
        progressBar.setProgress((int) (phim.getDiem() * 10));

        return mView;
    }
}
