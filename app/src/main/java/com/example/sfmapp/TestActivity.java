package com.example.sfmapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class TestActivity extends AppCompatActivity {
    Button backBtn,off,on,ok,up,down;
    EditText temp;
    TextView tempDisplay;
    int defaultTemp=24;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //assign views
        temp=findViewById(R.id.tempManual);
        ok=findViewById(R.id.OK_temp);
        backBtn=findViewById(R.id.backTest);
        off=findViewById(R.id.offTest);
        on=findViewById(R.id.onTest);
        up=findViewById(R.id.upTemp);
        down=findViewById(R.id.downTemp);
        tempDisplay=findViewById(R.id.tempAffichage);

        //display default temp
        tempDisplay.setText(String.valueOf(defaultTemp));

        //Back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(TestActivity.this,AddDeviceActivity.class);
                startActivity(goBack);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        //RC buttons
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference offRef= FirebaseDatabase.getInstance().getReference("Reference/"+GlobalVariablesJava.ref.getText().toString()+"/Buttons/Off/state");
                offRef.setValue("unclicked");
                offRef.setValue("clicked");
            }
        });
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference onRef= FirebaseDatabase.getInstance().getReference("Reference/"+GlobalVariablesJava.ref.getText().toString()+"/Buttons/"+String.valueOf(defaultTemp)+"/state");
                onRef.setValue("unclicked");
                onRef.setValue("clicked");
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference manualTempRef= FirebaseDatabase.getInstance().getReference("Reference/"+GlobalVariablesJava.ref.getText().toString()+"/Buttons/"+temp.getText().toString()+"/state");
                manualTempRef.setValue("unclicked");
                manualTempRef.setValue("clicked");
                defaultTemp=Integer.parseInt(temp.getText().toString());
                tempDisplay.setText(defaultTemp);
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultTemp++;
                DatabaseReference manualTempRef= FirebaseDatabase.getInstance().getReference("Reference/"+GlobalVariablesJava.ref.getText().toString()+"/Buttons/"+String.valueOf(defaultTemp)+"/state");
                manualTempRef.setValue("unclicked");
                manualTempRef.setValue("clicked");
                tempDisplay.setText(defaultTemp);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultTemp--;
                DatabaseReference manualTempRef= FirebaseDatabase.getInstance().getReference("Reference/"+GlobalVariablesJava.ref.getText().toString()+"/Buttons/"+String.valueOf(defaultTemp)+"/state");
                manualTempRef.setValue("unclicked");
                manualTempRef.setValue("clicked");
                tempDisplay.setText(defaultTemp);
            }
        });
    }
}
