package com.example.sfmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PreInstructionsActivity extends AppCompatActivity {
Button terminer;
    ProgressBar mPb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_instructions);

        mPb=findViewById(R.id.progressBar);
        mPb.setVisibility(ProgressBar.GONE);


        terminer=findViewById(R.id.terminer);
        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show circular progress bar
                mPb.setVisibility(ProgressBar.VISIBLE);
                // set Start to on in Firebase
                DatabaseReference startRef = FirebaseDatabase.getInstance().getReference("Actuals/start");
                startRef.setValue("off");
                startRef.setValue("on");
                // go to next activity
                Intent goInstruct = new Intent(PreInstructionsActivity.this,InstructionsActivity.class);
                startActivity(goInstruct);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }
}