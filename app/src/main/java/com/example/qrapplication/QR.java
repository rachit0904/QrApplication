package com.example.qrapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class QR extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager=findViewById(R.id.pager2);
        PagerAdapter2 adapter=new PagerAdapter2(getSupportFragmentManager(),tabLayout.getTabCount(),this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageButton bck=findViewById(R.id.bck2);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QR.this,home.class);
                intent.putExtra("uid",getIntent().getStringExtra("uid"));
                startActivity(intent);
                finish();
            }
        });

    }
}