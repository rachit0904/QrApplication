package com.example.qrapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    TextView name;
    ImageView statusColor;
    SwipeRefreshLayout refreshLayout;
    int addedCount=-1;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<String> no=new ArrayList<>();
    ArrayList<String> uid=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        statusColor=findViewById(R.id.availibility);
        setSupportActionBar(toolbar);
        refreshLayout=findViewById(R.id.refreshLayout);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        name=findViewById(R.id.userName);
        ImageView user=findViewById(R.id.userImage);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,profilepage.class);
                intent.putExtra("uid",getIntent().getStringExtra("uid"));
                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkNetwork();
                refreshLayout.setRefreshing(false);
            }
        });

        //to get added contacts count
        myRef=database.getReference().child("users").child(getIntent().getStringExtra("uid"));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("name").getValue(String.class));
                if(snapshot.child("added_contacts").getChildrenCount()>0){
                    myRef.child("available_contacts").setValue((snapshot.child("added_contacts").getChildrenCount()));
                    addedCount=(int)(snapshot.child("added_contacts").getChildrenCount());
                }else{
                    myRef.child("available_contacts").setValue(0);
                    addedCount=0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //adds the scanned contact to list
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getIntent().getStringExtra("scanned uid")!=null && addedCount!=-1){
                    int userCount=addedCount+1;
                    myRef.child("added_contacts").child(String.valueOf(userCount)).child("name").setValue(getIntent().getStringExtra("scanned name"));
                    myRef.child("added_contacts").child(String.valueOf(userCount)).child("no").setValue(getIntent().getStringExtra("scanned no"));
                    myRef.child("added_contacts").child(String.valueOf(userCount)).child("uid").setValue(getIntent().getStringExtra("scanned uid"));
                    myRef.child("available_contacts").setValue(String.valueOf(userCount));
                }
            }
        },800);

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
        }
        else if(item.getItemId()==R.id.profile){
            Intent intent=new Intent(home.this,profilepage.class);
            intent.putExtra("uid",getIntent().getStringExtra("uid"));
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.inbox){
            Intent intent=new Intent(home.this,Inbox.class);
            intent.putExtra("uid",getIntent().getStringExtra("uid"));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
