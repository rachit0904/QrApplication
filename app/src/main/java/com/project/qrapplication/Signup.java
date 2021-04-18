package com.project.qrapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    ImageButton bck;
    Button save;
    EditText name,no,usname,pwd;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference();
        bck=findViewById(R.id.bckBtn);
        save=findViewById(R.id.saveBtn);
        name=findViewById(R.id.name);
        no=findViewById(R.id.contact);
        usname=findViewById(R.id.setusnme);
        pwd=findViewById(R.id.setpwd);
        bck.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==bck.getId()){
            finish();
        }
        else if(v.getId()==save.getId()){
            String email=usname.getText().toString();
            String password=pwd.getText().toString();
            if(usname.getText().toString().isEmpty() || pwd.getText().toString().isEmpty()
                 || name.getText().toString().isEmpty() || no.getText().toString().isEmpty()){
                Snackbar.make(v, "Fill missing details!", Snackbar.LENGTH_SHORT).show();
            }
            else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    myRef=database.getReference().child("users").child(user.getUid());
                                    myRef.child("name").setValue(name.getText().toString());
                                    myRef.child("no").setValue(no.getText().toString());
                                    myRef.child("email").setValue(email);
                                    myRef.child("pwd").setValue(password);
                                    myRef.child("uid").setValue(mAuth.getUid());
                                    updateUI(user);
                                    Intent intent=new Intent(Signup.this,readyToGo.class);
                                    intent.putExtra("uid",mAuth.getUid());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Snackbar.make(v, "Account creation failed!", Snackbar.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1800);
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        this.user=user;
    }
}