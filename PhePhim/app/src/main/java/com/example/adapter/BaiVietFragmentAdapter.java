package com.example.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.util.ChiaSeFragment;
import com.example.util.HoiDapFragment;
import com.example.util.PhanTichFragment;
import com.example.util.TatCaFragment;

public class BaiVietFragmentAdapter extends FragmentStatePagerAdapter {

    String listTab[] = {"Tất cả", "Phân tích", "Hỏi đáp", "Chia sẻ"};
    TatCaFragment tatCaFragment;
    PhanTichFragment phanTichFragment;
    HoiDapFragment hoiDapFragment;
    ChiaSeFragment chiaSeFragment;


    public BaiVietFragmentAdapter(FragmentManager fm) {
        super(fm);
        tatCaFragment = new TatCaFragment();
        phanTichFragment = new PhanTichFragment();
        hoiDapFragment = new HoiDapFragment();
        chiaSeFragment = new ChiaSeFragment();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return tatCaFragment;
            case 1:
                return phanTichFragment;
            case 2:
                return hoiDapFragment;
            case 3:
                return chiaSeFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
