package com.example.qrapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int noOfTabs;
    Context mContext;
    public PagerAdapter(@NonNull FragmentManager fm, int tabs, Context context) {
        super(fm);
        this.noOfTabs=tabs;
        mContext=context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Chats";
            } case 1:{
                return "Available";
            } case 2:{
                return "Calls";
            } default:{
                return null;
            }
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new tab1();
            } case 1:{
                return new tab2();
            } case 2:{
                return new tab3();
            } default:{
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
