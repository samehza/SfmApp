package com.example.sfmapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.sfmapp.GlobalVariablesJava.*;
public class UseDeviceActivity extends AppCompatActivity {
    Button off,on,ok,up,down,ac_list,settings;
    SharedPreferences sharedPref ;
    EditText temp;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_device);
        //shared pref to save the value of the current temperature, to be able to load it wheb app is destroyed and re-launched

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //assign views
        TextView tempDisplay=findViewById(R.id.tempAffichage);
        temp=findViewById(R.id.tempManual_EditText);
        ok=findViewById(R.id.temp_ok);
        off=findViewById(R.id.off);
        on=findViewById(R.id.on);
        up=findViewById(R.id.tempINC);
        down=findViewById(R.id.tempDEC);
        currentTemperature = sharedPref.getInt(getString(R.string.saved_currentTemperature), 24);
        //Display temperature
        tempDisplay.setText(String.valueOf(currentTemperature)+"°C");
        // temp control buttons
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (i=0; i< selected.size(); i++){
                    DatabaseReference offRef= FirebaseDatabase.getInstance().getReference("Reference/"+ selected.get(i)+"/Buttons/Off/state");
                    offRef.setValue("unclicked");
                    offRef.setValue("clicked");
                }
            }
        });
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (i=0; i< selected.size(); i++){
                    DatabaseReference onRef= FirebaseDatabase.getInstance().getReference("Reference/"+ selected.get(i)+"/Buttons/"+ generalTemp+"/state");
                    onRef.setValue("unclicked");
                    onRef.setValue("clicked");
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTemperature=Integer.parseInt(temp.getText().toString());
                if(currentTemperature > GlobalVariablesJava.maxTemp) {
                    currentTemperature=GlobalVariablesJava.maxTemp;
                    Toast.makeText(getApplicationContext(), "Vous avez dépasser la température maximale", Toast.LENGTH_LONG).show();
                    temp.setText(String.valueOf(currentTemperature));
                    tempDisplay.setText(currentTemperature+"°C");
                }
                if(currentTemperature<GlobalVariablesJava.minTemp){
                    currentTemperature=GlobalVariablesJava.minTemp;
                    Toast.makeText(getApplicationContext(), "Vous avez dépasser la température minimale", Toast.LENGTH_LONG).show();
                    temp.setText(String.valueOf(currentTemperature));
                    tempDisplay.setText(currentTemperature+"°C");
                }
                editor.putInt(getString(R.string.saved_currentTemperature), currentTemperature);
                editor.commit();
                tempDisplay.setText(currentTemperature+"°C");
                for (i=0; i< selected.size(); i++){
                    DatabaseReference manualTempRef= FirebaseDatabase.getInstance().getReference("Reference/"+ selected.get(i)+"/Buttons/"+ currentTemperature+"/state");
                    manualTempRef.setValue("unclicked");
                    manualTempRef.setValue("clicked");
                }
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentTemperature < GlobalVariablesJava.maxTemp) {
                    currentTemperature++;
                    editor.putInt(getString(R.string.saved_currentTemperature), currentTemperature);
                    editor.commit();
                    tempDisplay.setText(currentTemperature + "°C");
                    for (i = 0; i < selected.size(); i++) {
                        DatabaseReference manualTempRef = FirebaseDatabase.getInstance().getReference("Reference/" + selected.get(i) + "/Buttons/" + currentTemperature + "/state");
                        manualTempRef.setValue("unclicked");
                        manualTempRef.setValue("clicked");
                    }
                }
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentTemperature > GlobalVariablesJava.minTemp) {
                    currentTemperature--;
                    editor.putInt(getString(R.string.saved_currentTemperature), currentTemperature);
                    editor.commit();
                    tempDisplay.setText(String.valueOf(currentTemperature) + "°C");
                    for (i = 0; i < selected.size(); i++) {
                        DatabaseReference manualTempRef = FirebaseDatabase.getInstance().getReference("Reference/" + selected.get(i) + "/Buttons/" + currentTemperature + "/state");
                        manualTempRef.setValue("unclicked");
                        manualTempRef.setValue("clicked");
                    }
                }
            }
        });

        //navbar buttons
        ac_list=findViewById(R.id.ac_list);
        settings=findViewById(R.id.settings);
        ac_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected.clear();
                Intent goList=new Intent(UseDeviceActivity.this,MultipleSelectionActivity.class);
                startActivity(goList);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSet=new Intent(UseDeviceActivity.this,SettingsActivity.class);
                startActivity(goSet);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }
}