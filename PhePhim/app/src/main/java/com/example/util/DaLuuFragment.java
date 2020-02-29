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

import com.example.adapter.BaiVietAdapter;
import com.example.model.BaiViet;
import com.example.phephim.BaiVietDetailActivity;
import com.example.phephim.LoginActivity;
import com.example.phephim.R;
import com.example.service.APIUtils;
import com.example.service.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaLuuFragment extends Fragment {
    private View mRootView;
    ListView lvBaiViet;
    BaiVietAdapter adapter;
    TextView txtEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_daluu, container, false);

        loadData();

        return mRootView;
    }

    private void loadData() {
        lvBaiViet = mRootView.findViewById(R.id.lvBaiVietFDL);
        adapter = new BaiVietAdapter(getActivity(), R.layout.bai_viet_item);
        lvBaiViet.setAdapter(adapter);
        txtEmpty = mRootView.findViewById(R.id.txtEmptyFDL);

        DataClient dataClient = APIUtils.getData();
        final Call<List<BaiViet>> call = dataClient.getLuuBaiVietOfUser(LoginActivity.user.getEmail());
        call.enqueue(new Callback<List<BaiViet>>() {
            @Override
            public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                if(response.body().size() == 0) {
                    txtEmpty.setVisibility(View.VISIBLE);
                }
                else {
                    adapter.clear();
                    for (int i = 0; i < response.body().size(); i++){
                        adapter.add(response.body().get(i));
                        txtEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BaiViet>> call, Throwable t) {

            }
        });

        lvBaiViet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaiViet baiViet = adapter.getItem(position);
                Intent intent = new Intent(getContext(), BaiVietDetailActivity.class);
                intent.putExtra("BaiViet", baiViet);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
