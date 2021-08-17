package com.project.qrapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Selectcontacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcontacts);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar4);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        addFragment(new inviteContacts());
        ImageButton bck=findViewById(R.id.bckbutton2);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addFragment(Fragment fragment) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fragment_Container3, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.allcontactsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.refresh){
            addFragment(new inviteContacts());
        }else if(item.getItemId()==R.id.inviteUser){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String appUrl="https://drive.google.com/file/d/1EiHrnXIyO8ES6Dvy-n9M4kbQDfjuddXf/view?usp=sharing";
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Download app and sign up to get started and enjoy all social media benefits in one app! "+appUrl);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}