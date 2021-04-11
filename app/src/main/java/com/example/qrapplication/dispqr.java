package com.example.qrapplication;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class dispqr extends Fragment {
    ImageView qr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dispqr, container, false);
        qr=view.findViewById(R.id.qrdisp);
        String data=getActivity().getIntent().getStringExtra("uid");
        String type= QRGContents.Type.TEXT;
        {
            QRGEncoder qrgEncoder=new QRGEncoder(data,null, type,500);
            Bitmap qrBits=qrgEncoder.getBitmap();
            qr.setImageBitmap(qrBits);
        }

        return view;
    }
}