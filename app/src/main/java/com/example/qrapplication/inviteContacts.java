package com.example.qrapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Data.UsersData;

import RecyclerAdapter.Adapter3;

public class inviteContacts extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<UsersData> dataList;
  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_invitecontacts,null);
        TextView title=getActivity().findViewById(R.id.inboxTitle);
        ArrayList<String> names=getActivity().getIntent().getStringArrayListExtra("names");
        ArrayList<String> no=getActivity().getIntent().getStringArrayListExtra("no");
        ArrayList<String> uid=getActivity().getIntent().getStringArrayListExtra("uid");
      ImageButton bck=getActivity().findViewById(R.id.bckbutton);
      bck.setBackground(getResources().getDrawable(R.drawable.close));
      bck.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentManager manager=getActivity().getSupportFragmentManager();
              FragmentTransaction transaction=manager.beginTransaction();
              bck.setBackground(getResources().getDrawable(R.drawable.bck));
              title.setText("Inbox");
              transaction.replace(R.id.fragment_Container2, new chats());
              transaction.commit();
          }
      });
        title.setText("Contacts");
        recyclerView=view.findViewById(R.id.inviteRecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        dataList=new ArrayList<>();
        for(int i=0;i<names.size();i++){
            UsersData data=new UsersData(names.get(i),no.get(i));
            dataList.add(data);
        }
        adapter=new Adapter3(view.getContext(),dataList);
        recyclerView.setAdapter(adapter);
        return view;
    }


}