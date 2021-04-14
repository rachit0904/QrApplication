package com.example.qrapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Data.UsersData;
import RecyclerAdapter.Adapter2;

public class calls extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<UsersData> dataList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_calls, container, false);


        recyclerView=view.findViewById(R.id.callRecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        dataList=new ArrayList<>();
        for(int i=1;i<10;i++){
            UsersData data=new UsersData("User "+i,"9637216675");
            dataList.add(data);
        }
        adapter=new Adapter2(view.getContext(),dataList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}