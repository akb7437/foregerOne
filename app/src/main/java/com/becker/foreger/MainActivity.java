package com.becker.foreger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Creating variables and firebase variable
    private TextView register;
    private Button login;
    private EditText editEmail, editPassword;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing variables
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();


    }

    // Setting login and register buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity (new Intent(this, RegisterUser.class));
                break;

            case R.id.login:
                userLogin();
                break;

        }

    }

    // Creating login function
    private void userLogin() {

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();


        // Validating user input
        if(email.isEmpty()) {
            editEmail.setError("Enter a valid email.");
            editEmail.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Must be in proper email format!");
            editPassword.requestFocus();
            return;

        }

        if(password.isEmpty()) {
            editPassword.setError("Please enter your password.");
            editPassword.requestFocus();
            return;

        }

        // Checking authentication
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Home.class));
                }
                else{
                    Toast.makeText(MainActivity.this, "Login failed. Incorrect email or password.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}