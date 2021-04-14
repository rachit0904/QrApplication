package com.example.qrapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class scanqr extends Fragment {
    CodeScanner codeScanner;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    Button addBtn;TextView scannedName,scannedNo;
    CodeScannerView scannerView;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference,reference2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_scanqr, container, false);
        scannerView=view.findViewById(R.id.scannerView);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        TextView scannedText=view.findViewById(R.id.scannedText);
        codeScanner =new CodeScanner(view.getContext(),scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scannedText.setText(result.getText());
                        if(!scannedText.getText().toString().isEmpty()){
                            builder=new AlertDialog.Builder(getActivity());
                            View view1=getLayoutInflater().inflate(R.layout.profilecard,null);
                            addBtn=view1.findViewById(R.id.addBtn);
                            scannedName=view1.findViewById(R.id.scannedName);
                            scannedNo=view1.findViewById(R.id.scannedNo);
                            reference2=database.getReference().child("users").child(scannedText.getText().toString());
                            reference2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    scannedName.setText(snapshot.child("name").getValue(String.class));
                                    scannedNo.setText(snapshot.child("no").getValue(String.class));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            builder.setView(view1);
                            dialog=builder.create();
                            dialog.show();
                            addBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getActivity(),home.class);
                                    intent.putExtra("scanned uid",scannedText.getText().toString());
                                    intent.putExtra("scanned name",scannedName.getText().toString());
                                    intent.putExtra("scanned no",scannedNo.getText().toString());
                                    intent.putExtra("uid",getActivity().getIntent().getStringExtra("uid"));
                                    Toast.makeText(getContext(), "user added!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    codeScanner.startPreview();
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });
                        }
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestForCamera();
    }

    private void requestForCamera() {
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(getActivity(), "Camera Permission required!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void onPause() {
        super.onPause();
        codeScanner.releaseResources();
    }


}