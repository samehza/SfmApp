package com.example.sfmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsActivity extends AppCompatActivity {
Button ac,ac_list,signout;
FloatingActionButton save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //assign views
        ac=findViewById(R.id.ac);
        ac_list=findViewById(R.id.ac_list);
        save=findViewById(R.id.confirm);

        //navbar buttons
        ac_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goList=new Intent(SettingsActivity.this,MultipleSelectionActivity.class);
                startActivity(goList);
            }
        });

        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goAC=new Intent(SettingsActivity.this,UseDeviceActivity.class);
                startActivity(goAC);
            }
        });
    }
}