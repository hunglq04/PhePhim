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

import com.example.adapter.BaiVietAdapter;
import com.example.model.BaiViet;
import com.example.phephim.BaiVietDetailActivity;
import com.example.phephim.R;
import com.example.service.APIUtils;
import com.example.service.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoiDapFragment extends Fragment {
    private View mRootView;
    ListView lvHoiDap;
    BaiVietAdapter baiVietAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_hoidap, container, false);

        loadData();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        lvHoiDap = mRootView.findViewById(R.id.lvHoiDap);
        baiVietAdapter = new BaiVietAdapter(getActivity(), R.layout.bai_viet_item);
        lvHoiDap.setAdapter(baiVietAdapter);
        DataClient dataClient1 = APIUtils.getData();
        Call<List<BaiViet>> call = dataClient1.getBaiVietByTheLoai("2");
        call.enqueue(new Callback<List<BaiViet>>() {
            @Override
            public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                baiVietAdapter.clear();
                for (int i = 0; i < response.body().size(); i++){
                    baiVietAdapter.add(response.body().get(i));
                }
            }

            @Override
            public void onFailure(Call<List<BaiViet>> call, Throwable t) {

            }
        });

        lvHoiDap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BaiVietDetailActivity.class);
                intent.putExtra("BaiViet", baiVietAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
