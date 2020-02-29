package com.example.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.util.BaiVietFragment;
import com.example.util.DaLuuFragment;
import com.example.util.DanhGiaFragment;
import com.example.util.TaiKhoanFragment;

public class UserFragmentAdapter extends FragmentStatePagerAdapter {

    String listTab[] = {"Tài khoản", "Bài viết", "Đã lưu", "Đánh giá"};
    BaiVietFragment baiVietFragment;
    DaLuuFragment daLuuFragment;
    DanhGiaFragment danhGiaFragment;
    TaiKhoanFragment taiKhoanFragment;

    public UserFragmentAdapter(FragmentManager fm) {
        super(fm);
        baiVietFragment = new BaiVietFragment();
        daLuuFragment = new DaLuuFragment();
        danhGiaFragment = new DanhGiaFragment();
        taiKhoanFragment = new TaiKhoanFragment();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return taiKhoanFragment;
            case 1:
                return baiVietFragment;
            case 2:
                return daLuuFragment;
            case 3:
                return danhGiaFragment;
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
