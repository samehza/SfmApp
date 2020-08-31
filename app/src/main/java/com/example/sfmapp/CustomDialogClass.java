package com.example.sfmapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CustomDialogClass {
    public void showDialog(Activity activity){
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.custom_go_test);

    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    Button dialogButton = (Button) dialog.findViewById(R.id.buttonOk);
    dialogButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
            DatabaseReference MaxMinRef= FirebaseDatabase.getInstance().getReference("acRef/"+GlobalVariablesJava.currentAcRefInstaller+"/Buttons");
            MaxMinRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    GlobalVariablesJava.maxTemp= Integer.parseInt(snapshot.child("maxTemp").getValue().toString());
                    GlobalVariablesJava.minTemp= Integer.parseInt(snapshot.child("minTemp").getValue().toString());
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            Intent goTest=new Intent(activity.getApplicationContext(),TestActivity.class);
            activity.startActivity(goTest);
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    });

    dialog.show();

    }
}

