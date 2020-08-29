package com.example.sfmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {
Button ac,ac_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //navbar buttons
        ac=findViewById(R.id.ac);
        ac_list=findViewById(R.id.ac_list);

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