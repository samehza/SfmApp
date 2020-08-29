package com.example.sfmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class MultipleSelectionActivity extends AppCompatActivity {
ListView l;
int i;
Button ac,settings;
List<String> boitiers = new ArrayList<String>();
FirebaseUser uID = FirebaseAuth.getInstance().getCurrentUser() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_selection);
        l=findViewById(R.id.list);
        CustomProgress alert = new CustomProgress();
        alert.showDialog(MultipleSelectionActivity.this);
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(i=0;i<boitiers.size();i++)
                {
                    Log.d("size", String.valueOf(boitiers.size()));
                    //iterating through the ArrayList items to detect if they are checked, if they are, "true" is assigned the value of "selected" variable in firebase
                    if (l.isItemChecked(i))
                    {
                        GlobalVariablesJava.selected.add(boitiers.get(i));
                        DatabaseReference selectRef = FirebaseDatabase.getInstance().getReference("Reference/"+boitiers.get(i)+"/selected");
                        selectRef.setValue("true");
                    }
                }
                Toast.makeText(MultipleSelectionActivity.this,"Boitiers séletionnés!",Toast.LENGTH_SHORT).show();
            }
        });

        //On child added listener to construct the "boitier" ArrayList, containing all the references from Firebase
        DatabaseReference boitierRef = FirebaseDatabase.getInstance().getReference("Users/"+uID.getUid()+"/Ref");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                boitiers.add(dataSnapshot.getKey());
                //refresh ListView
                l.invalidateViews();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        boitierRef.addChildEventListener(childEventListener);

        //setting the adapter to display the items of the list "boitiers"
        l.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_multichoice,
                boitiers );
        l.setAdapter(arrayAdapter);

        //navbar
        ac=findViewById(R.id.ac);
        settings=findViewById(R.id.settings);

        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goAC= new Intent(MultipleSelectionActivity.this,UseDeviceActivity.class);
                startActivity(goAC);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSet= new Intent(MultipleSelectionActivity.this,UseDeviceActivity.class);
                startActivity(goSet);
            }
        });


    }




}