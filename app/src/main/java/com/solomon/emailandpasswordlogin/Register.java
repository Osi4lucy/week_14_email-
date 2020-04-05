package com.solomon.emailandpasswordlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText userFullName, userEmail, userPassWord;
    Button register;
    TextView loginBtn;
    FirebaseAuth auth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userFullName = findViewById(R.id.fullNameText);
        userEmail = findViewById(R.id.emailText);
        userPassWord = findViewById(R.id.passwordText);
        register = findViewById(R.id.loginButton);
        loginBtn = findViewById(R.id.createText);

        auth=FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        // check if the user is already logged in

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassWord.getText().toString();

                if(TextUtils.isEmpty(email)){
                    userEmail.setError("Email is required. ");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    userPassWord.setError("Password is required.    ");
                    return;
                }
                if(password.length() < 6){
                    userPassWord.setError("Password must be greater than 6 chars");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "New user registered ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "An error has occured! " + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
