package com.project.rentaway;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpPage extends AppCompatActivity {

    private static final String TAG="signUpPage";
    private EditText user1,pass1,phone1,email1;
    private Button register1;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    private void registerUser(){

        String email = email1.getText().toString().trim();
        String password = pass1.getText().toString().trim();
        String phone = phone1.getText().toString().trim();
        String user = user1.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            toastMessage("Please enter the Email ID.");
            return;
        }
        if(TextUtils.isEmpty(password)){
            toastMessage("Please enter the Password.");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            toastMessage("Please enter the Phone No.");
            return;
        }if(TextUtils.isEmpty(user)){
            toastMessage("Please enter the Username.");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    toastMessage("User registered successfully.");
                                    finish();
                                    //Intent intent = new Intent(this,mainPage.class);
                                    //startActivity(intent);
                                }
                                else
                                {
                                    toastMessage("User could not be registered. Please try again.");
                                }
                            }
                        }
                );
    }

    private void toastMessage(String message){
        Toast.makeText(signUpPage.this,message,Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2);
        Log.d(TAG, "onCreate: Starting...");

        //progressDialog = new ProgressDialog(this);
        user1 =(EditText)findViewById(R.id.userid);
        pass1 =(EditText)findViewById(R.id.password);
        register1 =(Button)findViewById(R.id.register);
        email1 =(EditText)findViewById(R.id.emailid);
        phone1=(EditText)findViewById(R.id.phoneNo);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            finish();
           // startActivity(new Intent(getApplicationContext(),mainPage.class));
        }
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Registering user...");
                registerUser();
            }
        });
    }
}
