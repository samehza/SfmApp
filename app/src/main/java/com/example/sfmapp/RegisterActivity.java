package com.example.sfmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference refUsers;
    private DatabaseReference compRef;
    private String firebaseUserID = "";
    private Button buttonReg,SignFromReg,backBtn;
    private EditText editTextTextUserNameReg,editTextTextEmailAddressReg,editTextTextPasswordReg,editTextcompany;
    CustomLoad alert = new CustomLoad();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //assign views
        backBtn=findViewById(R.id.backTest);
        mAuth = FirebaseAuth.getInstance();
        buttonReg = findViewById(R.id.buttonReg);
        SignFromReg = findViewById(R.id.SignFromReg);
        editTextTextUserNameReg = findViewById(R.id.editTextTextUserNameReg);
        editTextTextEmailAddressReg = findViewById(R.id.editTextTextEmailAddressReg);
        editTextTextPasswordReg = findViewById(R.id.editTextTextPasswordReg);
        editTextcompany = findViewById(R.id.editTextcompany);

        //Back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(RegisterActivity.this,WelcomeActivity.class);
                startActivity(goBack);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        //main buttons
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        SignFromReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, SigninActivity.class);
                startActivity(intent);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                RegisterActivity.this.finish();
            }
        });
    }

    private void registerUser() {
        String userName = editTextTextUserNameReg.getText().toString();
        String emailAddress = editTextTextEmailAddressReg.getText().toString();
        String password = editTextTextPasswordReg.getText().toString();
        String company = editTextcompany.getText().toString();
        if (userName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nom d'utilisateur ne peut pas être vide", Toast.LENGTH_SHORT).show();
        }
        else if (emailAddress.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Adresse Email ne peut pas être vide", Toast.LENGTH_SHORT).show();
        }
        else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Mot de passe ne peut pas être vide", Toast.LENGTH_SHORT).show();
        }
        else if (company.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Entreprise ne peut pas être vide", Toast.LENGTH_SHORT).show();
        }
        else{
            alert.showDialog(RegisterActivity.this);
            mAuth.createUserWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                String firebaseUserID = firebaseUser.getUid();
                                DatabaseReference refUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUserID);
                                DatabaseReference compRef = FirebaseDatabase.getInstance().getReference("Companies/" + company);
                                compRef.setValue(firebaseUserID);


                                HashMap userHashMap = new HashMap();
                                userHashMap.put("uid",firebaseUserID);
                                userHashMap.put("username",userName);
                                userHashMap.put("company",company);
                                refUsers.updateChildren(userHashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(RegisterActivity.this, MultipleSelectionActivity.class);
                                            alert.hideDialog();
                                            startActivity(intent);
                                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                            RegisterActivity.this.finish();
                                        }
                                        else alert.hideDialog();
                                    }
                                });
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                alert.hideDialog();
                                Toast.makeText(getApplicationContext(), "Error creating account:\n" + task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}