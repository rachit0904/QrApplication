package com.example.qrapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    TextView name;
    ImageView statusColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        statusColor=findViewById(R.id.availibility);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        name=findViewById(R.id.userName);
        myRef=database.getReference().child("users").child(getIntent().getStringExtra("uid")).child("name");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        TabLayout tabLayout = findViewById(R.id.tabs);
        checkNetwork();
        ViewPager viewPager=findViewById(R.id.pager);
        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetwork();
    }

    private void checkNetwork() {
        boolean connection=isNetworkAvailable();
        if(connection){
            statusColor.setImageDrawable(getResources().getDrawable(R.drawable.available));
        }
        else{
            statusColor.setImageDrawable(getResources().getDrawable(R.drawable.offline));
        }
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homepagemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.signout){
            mAuth.signOut();
            startActivity(new Intent(home.this,LoginPage.class));
            finish();
        }else if(item.getItemId()==R.id.profile){
            Intent intent=new Intent(home.this,profilepage.class);
            intent.putExtra("uid",getIntent().getStringExtra("uid"));
            startActivity(intent);
        }else if(item.getItemId()==R.id.scan){
            Intent intent=new Intent(home.this,QR.class);
            intent.putExtra("uid",getIntent().getStringExtra("uid"));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}