package com.example.sfmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.sfmapp.GlobalVariablesJava.currentTemperature;

public class SettingsActivity extends AppCompatActivity {
    int i;
    SharedPreferences sharedPref;
    Button ac,ac_list,signout;
    FloatingActionButton save;
    EditText generalTemp, magneticSensor, presenceSensor;
    FirebaseUser uID = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //shared pref to save the value of the general temperature, to be able to load it when app is destroyed and re-launched
        sharedPref = this.getSharedPreferences("SettingPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        GlobalVariablesJava.generalTemp = sharedPref.getInt(getString(R.string.saved_generalTemperature), 26);


        //assign views
        ac=findViewById(R.id.ac);
        ac_list=findViewById(R.id.ac_list);
        save=findViewById(R.id.confirm);
        signout=findViewById(R.id.signout);
        generalTemp=findViewById(R.id.generalTemp);
        magneticSensor=findViewById(R.id.magneticSensor);
        presenceSensor=findViewById(R.id.presenceSensor);

        //signout button
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SettingsActivity.this, WelcomeActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                SettingsActivity.this.finish();
            }
        });

        //save settings on "OK" floating button press
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value_temp=generalTemp.getText().toString();
                String value_magnetic=magneticSensor.getText().toString();
                String value_presence=presenceSensor.getText().toString();
                if (value_temp.isEmpty() && value_magnetic.isEmpty() && value_presence.isEmpty()){
                    Toast.makeText(SettingsActivity.this,"Aucune modification effectuée",Toast.LENGTH_SHORT).show();
                }
                else{
                    GlobalVariablesJava.generalTemp=Integer.parseInt(value_temp);
                    editor.putInt(getString(R.string.generalTemp_key), GlobalVariablesJava.generalTemp);
                    editor.commit();
                    for(i=0;i<GlobalVariablesJava.selected.size();i++){
                        DatabaseReference configRef=FirebaseDatabase.getInstance().getReference("Users/"+uID.getUid()+"/Ref/"+GlobalVariablesJava.selected.get(i)+"/config");
                        if (!value_magnetic.isEmpty())
                            configRef.child("magneticSensor").setValue(value_magnetic);
                        if (!value_presence.isEmpty())
                            configRef.child("presenceSensor").setValue(value_presence);
                        if (!value_temp.isEmpty())
                            configRef.child("generalTemperature").setValue(value_temp);
                    }
                    Toast.makeText(SettingsActivity.this,"Paramètres enregistrés",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //navbar buttons
        ac_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goList=new Intent(SettingsActivity.this,MultipleSelectionActivity.class);
                startActivity(goList);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goAC=new Intent(SettingsActivity.this,UseDeviceActivity.class);
                startActivity(goAC);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }
}