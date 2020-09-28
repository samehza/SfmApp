package com.example.sfmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import static com.example.sfmapp.GlobalVariablesJava.selected;

public class MultipleSelectionActivity extends AppCompatActivity {
    //progress bar until selected items are saved to Firebase before going to the "use device activity"
    CustomLoad load = new CustomLoad();
    ListView l;
    int i;
    Button ac,settings;
    List<String> boitiers = new ArrayList<String>();
    List<String> emplacement = new ArrayList<String>();
    FirebaseUser uID = FirebaseAuth.getInstance().getCurrentUser() ;
    int maxIterator;
    int minIterator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_selection);
        l=findViewById(R.id.list);

        //dialog  for progress bar loading the elements of the list
        CustomProgress alert = new CustomProgress();
        alert.showDialog(MultipleSelectionActivity.this);

        //confirm button
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(i=0;i<boitiers.size();i++)
                {
                    //iterating through the ArrayList items to detect if they are checked, if they are, "true" is assigned the value of "selected" variable in firebase
                    if (l.isItemChecked(i))
                    {
                        GlobalVariablesJava.emplacementSelected.add(emplacement.get(i));
                        GlobalVariablesJava.selected.add(boitiers.get(i));
                    }
                }
                if (selected.size()>0)
                    Toast.makeText(MultipleSelectionActivity.this,"Boitiers séletionnés!",Toast.LENGTH_SHORT).show();
                else Toast.makeText(MultipleSelectionActivity.this,"Aucun boîtier sélectionné!",Toast.LENGTH_SHORT).show();
            }
        });

        //On child added listener to construct the "boitier" ArrayList, containing all the references from Firebase
        DatabaseReference boitierRef = FirebaseDatabase.getInstance().getReference("Users/"+uID.getUid()+"/Ref");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                boitiers.add(dataSnapshot.getKey());
                if(dataSnapshot.child("emplacement").getValue()!=null)
                    emplacement.add(String.valueOf(dataSnapshot.child("emplacement").getValue()));

                //refresh ListView
                l.invalidateViews();
                alert.hideDialog();
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
        boitierRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() == 0){
                    // db has no children
                    alert.hideDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //setting the adapter to display the items of the list "boitiers"
        l.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_multichoice,
                emplacement );
        l.setAdapter(arrayAdapter);

        //navbar
        ac=findViewById(R.id.ac);
        settings=findViewById(R.id.settings);
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (selected.size() > 0){
                        load.showDialog(MultipleSelectionActivity.this);
                        updateMaxMin();
                    }

                    else
                        Toast.makeText(MultipleSelectionActivity.this, "Sélectionnez l'emplacement puis appuyez sur OK", Toast.LENGTH_SHORT).show();

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSet= new Intent(MultipleSelectionActivity.this,SettingsActivity.class);
                startActivity(goSet);
            }
        });
    }

    private void updateMaxMin() {
        maxIterator = 0;
        minIterator = 0;
        getMaxTemp();
        getMinTemp();
        Log.d("here 0 size", String.valueOf(selected.size()));
    }

    private void getMaxTemp() {
        DatabaseReference maxTempRef= FirebaseDatabase.getInstance().getReference("Reference/"+ selected.get(maxIterator)+"/Buttons/maxTemp");
        maxTempRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long tem=0;
                if (dataSnapshot!=null)
                    tem = (long) dataSnapshot.getValue();
                maxIterator++;
                Log.d("here 1 itt", String.valueOf(maxIterator));
                if(maxIterator==1){
                    GlobalVariablesJava.maxTemp=(int)tem;
                    Log.d("here 2 temp", String.valueOf(GlobalVariablesJava.maxTemp));

                }
                else if((int)tem < GlobalVariablesJava.maxTemp){
                    GlobalVariablesJava.maxTemp=(int)tem;
                    Log.d("here 3 temp", String.valueOf(GlobalVariablesJava.maxTemp));
                }
                if(maxIterator==selected.size()){
                    if((minIterator==selected.size())){
                        load.hideDialog();
                        Intent goAC= new Intent(MultipleSelectionActivity.this,UseDeviceActivity.class);
                        startActivity(goAC);
                        MultipleSelectionActivity.this.finish();
                    }
                }
                else{
                    getMaxTemp();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void getMinTemp() {
        DatabaseReference maxTempRef= FirebaseDatabase.getInstance().getReference("Reference/"+ selected.get(minIterator)+"/Buttons/minTemp");
        maxTempRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long tem = (long) dataSnapshot.getValue();
                minIterator++;
                if(minIterator==1){
                    GlobalVariablesJava.minTemp=(int)tem;
                }
                else if((int)tem > GlobalVariablesJava.minTemp){
                    GlobalVariablesJava.minTemp=(int)tem;
                }
                if(minIterator==selected.size()){
                    if((maxIterator==selected.size())){
                        load.hideDialog();
                        Intent goAC= new Intent(MultipleSelectionActivity.this,UseDeviceActivity.class);
                        startActivity(goAC);
                        MultipleSelectionActivity.this.finish();
                    }
                }
                else{
                    getMinTemp();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}