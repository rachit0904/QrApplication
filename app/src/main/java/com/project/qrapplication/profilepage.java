package com.project.qrapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class profilepage extends AppCompatActivity {
    FirebaseAuth auth;
    TextView changeProfilePic;
    CircleImageView dp;
    Uri imageUri;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference,profileRef;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        profileRef=storage.getReference().child("users").child(getIntent().getStringExtra("uid"));
        changeProfilePic=findViewById(R.id.textView5);
        dp=findViewById(R.id.imageView3);
        FloatingActionButton fab = findViewById(R.id.fab);
        TextInputEditText name=findViewById(R.id.usname);
        TextInputEditText no=findViewById(R.id.usno);
        TextInputEditText email=findViewById(R.id.usmail);
        TextInputEditText dob=findViewById(R.id.usdob);

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                reference.child("profile_pic_url").setValue(uri.toString());
                Picasso.get().load(uri).into(dp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profilepage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                }
                else if(flag==false){
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

        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view=v;
                choosePicture();
            }
        });

    }

    private void choosePicture() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            dp.setImageURI(imageUri);
            uploadPicture(view);
        }
    }

    private void uploadPicture(View v) {
        String imageFileName=getIntent().getStringExtra("uid");
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading image...");
        progressDialog.show();
        StorageReference profilePic=storageReference.child("users/"+imageFileName);
        profilePic.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Snackbar.make(v,"Image Uploaded!",Snackbar.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar.make(v,"Image Upload Failed!",Snackbar.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("Progress : "+ (int)progress + "%");
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
