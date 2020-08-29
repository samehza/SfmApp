package com.example.sfmapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddDeviceActivity extends AppCompatActivity {
    private String uID="";
    private EditText emp,comp,clim;
    private Button valider;
    List<String> companies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_device);
        super.onCreate(savedInstanceState);

        emp=findViewById(R.id.emplacement);
        GlobalVariablesJava.ref=findViewById(R.id.refboitier);
        comp=findViewById(R.id.company);
        valider=findViewById(R.id.valider);
        clim=findViewById(R.id.refclim);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //string values
                String emplacement=emp.getText().toString();
                String reference=GlobalVariablesJava.ref.getText().toString();
                String company = comp.getText().toString();
                String ac=clim.getText().toString();
                //variables Firebase references

                DatabaseReference refRef= FirebaseDatabase.getInstance().getReference("Actuals/actualRef");
                refRef.setValue(reference);
                //Actual clim ref
                DatabaseReference refClim=FirebaseDatabase.getInstance().getReference("Actuals/refClim");
                refClim.setValue(ac);


                //Retrieving UID
                DatabaseReference uidRef=FirebaseDatabase.getInstance().getReference("Companies/"+company);
                uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        uID = (String) snapshot.getValue();
                        Log.d("user is", uID);
                        DatabaseReference myPath=FirebaseDatabase.getInstance().getReference("Users/"+uID+"/Ref/"+reference);
                        myPath.setValue(emplacement);
                    }
                    @Override

                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                GlobalVariablesJava.instructionCounter=0;
                Intent intent= new Intent(AddDeviceActivity.this,PreInstructionsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                Toast.makeText(AddDeviceActivity.this, "Informations validées!", Toast.LENGTH_SHORT).show();

            }
        });

        DatabaseReference compRef = FirebaseDatabase.getInstance().getReference("Companies");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                companies.add(dataSnapshot.getKey());
                Log.d("myTag",dataSnapshot.getKey());
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
        compRef.addChildEventListener(childEventListener);


    }

}
