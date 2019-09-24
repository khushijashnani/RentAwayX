package com.project.rentaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText user,pass;
    private Button signup,login;
    private FirebaseAuth firebaseAuth;
    private static final String TAG ="MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");


        firebaseAuth = FirebaseAuth.getInstance();
        user =(EditText)findViewById(R.id.userid);
        pass =(EditText)findViewById(R.id.password);
        signup =(Button)findViewById(R.id.signupbtn);
        login = (Button) findViewById(R.id.btnlogin);

        if(firebaseAuth.getCurrentUser()!=null){
            finish();
           // startActivity(new Intent(getApplicationContext(),mainPage.class));
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked signup");
                finish();
                Intent intent = new Intent(MainActivity.this,signUpPage.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked login");
                userLogin();
            }
        });
    }
    private void userLogin() {
        String email = user.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            toastMessage("Please enter the email.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            toastMessage("Please enter the Password.");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            finish();

                            Intent intentNew=new Intent(MainActivity.this,navigationDrawer.class);
                                    startActivity(intentNew);
                            //startActivity(new Intent(getApplicationContext(),mainPage.class));
                        }
                        else
                        {
                            toastMessage("Try again");
                        }

                    }
                });

    }

    private void toastMessage(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }

}
