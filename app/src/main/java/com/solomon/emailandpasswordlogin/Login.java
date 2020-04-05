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

public class Login extends AppCompatActivity {

    EditText userEmail, userPassWord;
    Button loginBtn;
    TextView createBtn;
    ProgressBar progressBar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.emailText);
        userPassWord = findViewById(R.id.passwordText);
        loginBtn = findViewById(R.id.loginButton);
        createBtn = findViewById(R.id.createText);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        loginBtn.setOnClickListener(new View.OnClickListener() {
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

//              progressBar.setVisibility(View.VISIBLE);

                // autheticate the user

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "User logged in ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "An error has occured! " + task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}
