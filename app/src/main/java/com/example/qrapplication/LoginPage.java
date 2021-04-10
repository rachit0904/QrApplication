package com.example.qrapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    Button loginBtn;
    TextView  signUp,forgetpwd;
    EditText usname,pwd;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        loginBtn=findViewById(R.id.loginBtn);
        signUp=findViewById(R.id.signupBtn);
        signUp.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        usname=findViewById(R.id.usernme);
        pwd=findViewById(R.id.pswd);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user!=null){
//            Toast.makeText(this, "Welcome "+user.getEmail(), Toast.LENGTH_SHORT).show();
//            finish();
//            startActivity(new Intent(LoginPage.this,generator.class));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==signUp.getId()){
            startActivity(new Intent(LoginPage.this,Signup.class));
        }
        else if(v.getId()==loginBtn.getId()){
            String username=usname.getText().toString();
            String pswd=pwd.getText().toString();
            if(username.isEmpty() || pswd.isEmpty()){
                Snackbar.make(v,"Enter Valid credentials!",Snackbar.LENGTH_SHORT).show();
            }else{
                signin(username,pswd);
            }
        }
    }

    private void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginPage.this, " signin success", Toast.LENGTH_SHORT).show();
                        updateUI(user);
                        finish();
                        startActivity(new Intent(LoginPage.this,generator.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginPage.this, "signin failiure", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // ...
                });
    }

    private void updateUI(FirebaseUser user) {
        this.user=user;
    }
}