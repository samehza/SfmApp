package com.example.sfmapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button RegFromSign, buttonSign,backBtn;
    private EditText editTextTextEmailAddressSign, editTextTextPasswordSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //asign views
        backBtn=findViewById(R.id.backTest);
        mAuth = FirebaseAuth.getInstance();
        buttonSign = findViewById(R.id.buttonSign);
        editTextTextEmailAddressSign = findViewById(R.id.editTextTextEmailAddressSign);
        editTextTextPasswordSign = findViewById(R.id.editTextTextPasswordSign);
        RegFromSign = findViewById(R.id.RegFromSign);

        //Back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(SigninActivity.this,WelcomeActivity.class);
                startActivity(goBack);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        //main buttons
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        RegFromSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                SigninActivity.this.finish();
            }
        });
    }

    private void loginUser() {
        String emailAddress = editTextTextEmailAddressSign.getText().toString();
        String password = editTextTextPasswordSign.getText().toString();
        if (emailAddress.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email Address can't be empty", Toast.LENGTH_LONG).show();
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password can't be empty", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    checkIfUserExists(user.getUid());
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Error Logging in:\n" + task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void checkIfUserExists(String userId) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("Users").child(userId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent;
                if (snapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Vous êtes connectés", Toast.LENGTH_LONG).show();
                    intent = new Intent(SigninActivity.this, MultipleSelectionActivity.class);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Toast.makeText(getApplicationContext(), "Connecté comme installateur", Toast.LENGTH_LONG).show();
                    intent = new Intent(SigninActivity.this, AddDeviceActivity.class);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                startActivity(intent);
                SigninActivity.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }
}