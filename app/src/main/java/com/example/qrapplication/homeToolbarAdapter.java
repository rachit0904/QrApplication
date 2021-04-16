package com.example.qrapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class homeToolbarAdapter extends FragmentPagerAdapter {
    final private int noOfTabs;
    final Context mContext;
    public homeToolbarAdapter(@NonNull FragmentManager fm, int tabs, Context context) {
        super(fm);
        this.noOfTabs=tabs;
        mContext=context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Feeds";
            }
            case 1:{
                return "Explore";
            } case 2:{
                return "Events";
            } case 3:{
                return "Calls";
            }default:{
                return null;
            }
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new feeds();
            } case 1:{
                return new explore();
            }  case 2:{
                return new events();
            }
            case 3:{
                return new calls();
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
