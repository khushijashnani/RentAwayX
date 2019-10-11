package com.project.rentaway;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.auth.FirebaseUser;

public class signUpPage extends AppCompatActivity {

    private static final String TAG="signUpPage";
    private EditText user1,pass1,phone1,email1;
    private Button register1;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseUser user2 ;
    private TextView text4,text5,text6,text7;

    private void registerUser(){

       final String email = email1.getText().toString().trim();
        final String password = pass1.getText().toString().trim();
        final String phone = phone1.getText().toString().trim();
       final String user = user1.getText().toString().trim();

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
        progressDialog.setMessage("Registering user...");
        progressDialog.show();



        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    user2 = firebaseAuth.getCurrentUser();
                                    ((FirebaseUser) user2).sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        toastMessage("User registered successfully. Please check your email for verification");
                                                        Log.d(TAG, "onComplete: new User registered");
                                                        Log.d(TAG, "Email sent.");
                                                        toastMessage("Verification email sent.");
                                                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
                                                        User userNew=new User(firebaseAuth.getCurrentUser().getUid(),email,user,password,phone);
                                                        databaseReference.child(user).setValue(userNew);
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(signUpPage.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                    finish();

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

        progressDialog = new ProgressDialog(this);
        user1 =(EditText)findViewById(R.id.userid);
        pass1 =(EditText)findViewById(R.id.password);
        register1 =(Button)findViewById(R.id.register);
        email1 = (EditText)findViewById(R.id.emailid);
        phone1=(EditText)findViewById(R.id.phoneNo);
        firebaseAuth = FirebaseAuth.getInstance();
        text4=(TextView)findViewById(R.id.textView4);
        text5=(TextView)findViewById(R.id.textView5);
        text6=(TextView)findViewById(R.id.textView6);
        text7=(TextView)findViewById(R.id.textView7);


        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Registering user...");
                registerUser();
            }
        });
    }
}
