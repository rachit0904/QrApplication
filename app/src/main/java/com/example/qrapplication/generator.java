package com.example.qrapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class generator extends AppCompatActivity {
    EditText editText;
    Button genQr,scanQr;
    ImageView qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        editText=findViewById(R.id.textVal);
        genQr=findViewById(R.id.qrGenBtn);
        scanQr=findViewById(R.id.qrScanBtn);
        qr=findViewById(R.id.qr);

        String data=getIntent().getStringExtra("uid");
        //editText.setText(data);
        editText.setVisibility(View.INVISIBLE);
        genQr.setVisibility(View.INVISIBLE);
        generate(data);

        genQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.isEmpty()){
                    Snackbar.make(v,"enter value!",Snackbar.LENGTH_SHORT).show();
                }else{
                    generate(data);
                }
            }
        });

        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(generator.this,scanner.class));
            }
        });


    }

    private void generate(String data) {
        String type=QRGContents.Type.TEXT;
        {
            QRGEncoder qrgEncoder=new QRGEncoder(data,null, type,500);
            Bitmap qrBits=qrgEncoder.getBitmap();
            qr.setImageBitmap(qrBits);
        }
    }
}