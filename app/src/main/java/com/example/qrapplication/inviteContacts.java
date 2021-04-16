package com.example.qrapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Data.UsersData;

import Data.contactsData;
import RecyclerAdapter.Adapter3;

public class inviteContacts extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<UsersData> dataList;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<String> no=new ArrayList<>();
    ArrayList<String> uid=new ArrayList<>();

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_invitecontacts,null);
        TextView title=getActivity().findViewById(R.id.inboxTitle);

      auth= FirebaseAuth.getInstance();
      database= FirebaseDatabase.getInstance();
      reference=database.getReference().child("users").child(auth.getUid());

      reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (int i = 1; i <= (int)(snapshot.child("added_contacts").getChildrenCount()); i++) {
                  names.add( snapshot.child("added_contacts").child("" + i).child("name").getValue(String.class));
                  no.add( snapshot.child("added_contacts").child("" + i).child("no").getValue(String.class));
                  uid.add( snapshot.child("added_contacts").child("" + i).child("uid").getValue(String.class));
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<names.size();i++){
                    UsersData data=new UsersData(names.get(i),no.get(i));
                    dataList.add(data);
                }
                adapter=new Adapter3(view.getContext(),dataList);
                recyclerView.setAdapter(adapter);
            }
        },400);
        return view;
    }


}