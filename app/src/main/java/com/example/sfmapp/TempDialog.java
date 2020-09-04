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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;


public class TempDialog{
    int i=0;
    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.temp_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //assign views
        TextView emplacement= dialog.findViewById(R.id.emplacementTemp);
        TextView realTemp= dialog.findViewById(R.id.realTemp);
        FloatingActionButton previous = dialog.findViewById(R.id.previous);
        FloatingActionButton next = dialog.findViewById(R.id.next);
        FloatingActionButton close = dialog.findViewById(R.id.close);

        //initialise textviews
        emplacement.setText(GlobalVariablesJava.emplacementSelected.get(0));
        realTemp.setText(GlobalVariablesJava.tempList.get(0));
        previous.setVisibility(View.INVISIBLE);
        if(GlobalVariablesJava.selected.size()==1){
            next.setVisibility(View.INVISIBLE);
        }
        dialog.show();
        //Buttons
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i<GlobalVariablesJava.selected.size()-1){
                    i++;
                    emplacement.setText(GlobalVariablesJava.emplacementSelected.get(i));
                    realTemp.setText(GlobalVariablesJava.tempList.get(i));
                    if(i==GlobalVariablesJava.selected.size()-1){
                        next.setVisibility(View.INVISIBLE);
                    }
                    if(i==1){
                        previous.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i>0){
                    i--;
                    emplacement.setText(GlobalVariablesJava.emplacementSelected.get(i));
                    realTemp.setText(GlobalVariablesJava.tempList.get(i));
                    if(i==0){
                        previous.setVisibility(View.INVISIBLE);
                    }
                    if(i==GlobalVariablesJava.selected.size()-2){
                        next.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}