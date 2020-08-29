package com.example.sfmapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Log.d("user", GlobalVariablesJava.ref.getText().toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);



        DatabaseReference addChild = FirebaseDatabase.getInstance().getReference("Reference");
        addChild.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               if (snapshot.getKey().equals(GlobalVariablesJava.ref.getText().toString())){
                   /*Intent goTest=new Intent(InstructionsActivity.this,TestActivity.class);
                   startActivity(goTest);*/
                   CustomDialogClass alert = new CustomDialogClass();
                   alert.showDialog(InstructionsActivity.this);
               }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
