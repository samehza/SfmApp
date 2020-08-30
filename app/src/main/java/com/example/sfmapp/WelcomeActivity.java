package com.example.sfmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    Button buttonGoReg;
    Button buttonGoSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        buttonGoReg =findViewById(R.id.buttonGoReg);
        buttonGoSign =findViewById(R.id.buttonGoSign);

        buttonGoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });
        buttonGoSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, SigninActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });

    }
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getInstance().getCurrentUser();
        if(firebaseUser!=null){
            checkIfUserExists(firebaseUser.getUid());
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
                    intent = new Intent(WelcomeActivity.this, MultipleSelectionActivity.class);
                } else {
                    intent = new Intent(WelcomeActivity.this, AddDeviceActivity.class);
                }
                startActivity(intent);
                WelcomeActivity.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }
}