package com.becker.foreger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    public TextView title;
    public Button registerUser;
    public EditText editPassword, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        title = (TextView) findViewById(R.id.newUserTitle);
        title.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.CreateAccount);
        registerUser.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.NewUserEmail);
        editPassword = (EditText) findViewById(R.id.newUserPassword);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.CreateAccount:
                registerUser();
                break;
        }

    }

    public void registerUser() {

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Enter a valid Email");
            editEmail.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            editPassword.setError("Enter a password");
            editPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please provide correct Email format");
            editEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Password must be more than 6 characters");
            editPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            User user = new User(email);
                            startActivity(new Intent(RegisterUser.this, MainActivity.class));

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                              @Override
                                                                              public void onComplete(@NonNull Task<Void> task) {
                                                                                  if (task.isSuccessful()) {
                                                                                      Toast.makeText(RegisterUser.this, "Registered", Toast.LENGTH_SHORT).show();

                                                                                  }

                                                                              }

                                                                          }

                                                                     );

                                                                }

                                                            }

                                                        }

                                                    );

                                    }

            }
