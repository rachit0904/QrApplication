package com.example.qrapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class readyToGo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_to_go);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(readyToGo.this, ShareQr.class);
                intent.putExtra("uid",getIntent().getStringExtra("uid"));
                startActivity(intent);
                finish();
            }
        },2400);
    }
}