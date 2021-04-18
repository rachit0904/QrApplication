package com.project.qrapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import Data.UsersData;

import RecyclerAdapter.Adapter3;

public class inviteContacts extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<UsersData> dataList;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> uid=new ArrayList<>();
    FirebaseStorage storage;

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_invitecontacts,null);
      TextView subTitle=getActivity().findViewById(R.id.selectSubTitle);
      auth= FirebaseAuth.getInstance();
      database= FirebaseDatabase.getInstance();
      reference=database.getReference().child("users");
      storage= FirebaseStorage.getInstance();

      reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (int i = 1; i <= (int)(snapshot.child(auth.getUid()).child("added_contacts").getChildrenCount()); i++) {
                  uid.add( snapshot.child(auth.getUid()).child("added_contacts").child("" + i).child("uid").getValue(String.class));
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

        recyclerView=view.findViewById(R.id.inviteRecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        dataList=new ArrayList<>();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(uid.size()>0){
//                    subTitle.setText(uid.size());
//                    subTitle.setVisibility(View.VISIBLE);
//                }
                for(String uid : uid){
                    Toast.makeText(getActivity(), uid, Toast.LENGTH_SHORT).show();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UsersData data = new UsersData(
                                    snapshot.child(uid).child("name").getValue(String.class) ,
                                    snapshot.child(uid).child("no").getValue(String.class),
                                    snapshot.child(uid).child("profile_pic_url").getValue(String.class)
                            );
                            dataList.add(data);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                Toast.makeText(getActivity(), String.valueOf(dataList.size()), Toast.LENGTH_SHORT).show();
                adapter=new Adapter3(view.getContext(),dataList);
                recyclerView.setAdapter(adapter);
            }
        },500);

        return view;
    }
}
