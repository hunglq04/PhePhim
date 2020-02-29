package com.example.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.BinhLuanPhimAdapter;
import com.example.adapter.DanhGiaPhimAdapter;
import com.example.model.BinhLuanPhim;
import com.example.model.Phim;
import com.example.phephim.LoginActivity;
import com.example.phephim.PhimDetailActivity;
import com.example.phephim.R;
import com.example.service.APIUtils;
import com.example.service.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhGiaFragment extends Fragment {
    private View mRootView;
    ListView lvDanhGia;
    DanhGiaPhimAdapter adapter;
    TextView txtEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_danhgia, container, false);

        loadData();

        return mRootView;
    }

    private void loadData() {
        lvDanhGia = mRootView.findViewById(R.id.lvDanhGiaFragment);
        adapter = new DanhGiaPhimAdapter(getActivity(), R.layout.danh_gia_phim_item);
        lvDanhGia.setAdapter(adapter);
        txtEmpty = mRootView.findViewById(R.id.txtEmptyFDG);

        DataClient dataClient = APIUtils.getData();
        Call<List<BinhLuanPhim>> call = dataClient.getAllBinhLuanPhim(LoginActivity.user.getEmail());
        call.enqueue(new Callback<List<BinhLuanPhim>>() {
            @Override
            public void onResponse(Call<List<BinhLuanPhim>> call, Response<List<BinhLuanPhim>> response) {
                if(response.body().size() == 0) {
                    txtEmpty.setVisibility(View.VISIBLE);
                } else {
                    adapter.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        adapter.add(response.body().get(i));
                        txtEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BinhLuanPhim>> call, Throwable t) {

            }
        });

        lvDanhGia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataClient dataClient1 = APIUtils.getData();
                Call<List<Phim>> call1 = dataClient1.getPhimHot(adapter.getItem(position).getMaPhim());
                call1.enqueue(new Callback<List<Phim>>() {
                    @Override
                    public void onResponse(Call<List<Phim>> call, Response<List<Phim>> response) {
                        Intent intent = new Intent(getContext(), PhimDetailActivity.class);
                        intent.putExtra("Phim", response.body().get(0));
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<Phim>> call, Throwable t) {

                    }
                });
            }
        });lvDanhGia = mRootView.findViewById(R.id.lvDanhGiaFragment);
        adapter = new DanhGiaPhimAdapter(getActivity(), R.layout.danh_gia_phim_item);
        lvDanhGia.setAdapter(adapter);
        txtEmpty = mRootView.findViewById(R.id.txtEmptyFDG);

        DataClient dataClient123 = APIUtils.getData();
        Call<List<BinhLuanPhim>> call123 = dataClient123.getAllBinhLuanPhim(LoginActivity.user.getEmail());
        call123.enqueue(new Callback<List<BinhLuanPhim>>() {
            @Override
            public void onResponse(Call<List<BinhLuanPhim>> call, Response<List<BinhLuanPhim>> response) {
                if(response.body().size() == 0) {
                    txtEmpty.setVisibility(View.VISIBLE);
                } else {
                    adapter.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        adapter.add(response.body().get(i));
                        txtEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BinhLuanPhim>> call, Throwable t) {

            }
        });

        lvDanhGia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataClient dataClient1 = APIUtils.getData();
                Call<List<Phim>> call1 = dataClient1.getPhimHot(adapter.getItem(position).getMaPhim());
                call1.enqueue(new Callback<List<Phim>>() {
                    @Override
                    public void onResponse(Call<List<Phim>> call, Response<List<Phim>> response) {
                        Intent intent = new Intent(getContext(), PhimDetailActivity.class);
                        intent.putExtra("Phim", response.body().get(0));
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<Phim>> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
