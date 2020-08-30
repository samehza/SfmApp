package com.example.sfmapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class WelcomeActivity extends AppCompatActivity {

    Button buttonGoReg;
    Button buttonGoSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        buttonGoReg =findViewById(R.id.buttonGoReg);
        buttonGoSign =findViewById(R.id.buttonGoSign);

        buttonGoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                WelcomeActivity.this.finish();
            }
        });
        buttonGoSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, SigninActivity.class);
                startActivity(intent);
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                WelcomeActivity.this.finish();
            }
        });
    }
}