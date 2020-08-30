package com.example.sfmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView orangeBar,logo;
    TextView smartRC;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        orangeBar=findViewById(R.id.orangeBar);
        smartRC=findViewById(R.id.title_app);
        logo=findViewById(R.id.sfm_logo);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //Set animation to elements
        orangeBar.setAnimation(topAnim);
        smartRC.setAnimation(bottomAnim);
        logo.setAnimation(topAnim);

    }
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getInstance().getCurrentUser();
        if(firebaseUser!=null){
            checkIfUserExists(firebaseUser.getUid());
        }
        else{
            Intent goWelcome= new Intent(SplashScreenActivity.this, WelcomeActivity.class);
            startActivity(goWelcome);
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
                    intent = new Intent(SplashScreenActivity.this, MultipleSelectionActivity.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, AddDeviceActivity.class);
                }
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }
}