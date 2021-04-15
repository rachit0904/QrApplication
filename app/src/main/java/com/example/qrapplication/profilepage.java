package com.example.qrapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class profilepage extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        FloatingActionButton fab = findViewById(R.id.fab);
        TextInputEditText name=findViewById(R.id.usname);
        TextInputEditText no=findViewById(R.id.usno);
        TextInputEditText email=findViewById(R.id.usmail);
        TextInputEditText dob=findViewById(R.id.usdob);
        reference=database.getReference().child("users").child(getIntent().getStringExtra("uid"));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("name").getValue(String.class));
                no.setText(snapshot.child("no").getValue(String.class));
                email.setText(snapshot.child("email").getValue(String.class));
                dob.setText(snapshot.child("dob").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            boolean flag=true;
            @Override
            public void onClick(View v) {
                if(flag==true) {
                    name.setEnabled(true);
                    no.setEnabled(true);
                    dob.setEnabled(true);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.done));
                    flag=false;
                }else if(flag==false){
                    reference.child("name").setValue(name.getText().toString());
                    reference.child("no").setValue(no.getText().toString());
                    reference.child("dob").setValue(dob.getText().toString());
                    Snackbar.make(v,"Saved",Snackbar.LENGTH_SHORT).show();
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.edit));
                    name.setEnabled(false);
                    no.setEnabled(false);
                    dob.setEnabled(false);
                    flag=true;
                }
            }
        });

        ImageButton bck=findViewById(R.id.bck);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.proiflepagemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.sc){
            Intent intent=new Intent(profilepage.this,QR.class);
            intent.putExtra("uid",getIntent().getStringExtra("uid"));
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
