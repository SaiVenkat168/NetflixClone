package com.example.netflixproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;
import com.example.netflixproject.mainscreens.MainScreen;

public class SignInActivity extends AppCompatActivity {
TextView d,c;
ProgressBar pb;
TextView buttonsignin,siginup,forgotpasswordextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        d=findViewById(R.id.d);
        c=findViewById(R.id.c);

        pb=findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        forgotpasswordextview=findViewById(R.id.forgotpasswordtextview);
        siginup=findViewById(R.id.textview);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setText("");
                d.setText("The information collected by Google reCAPTACHA is subjected to the Google Privary Policy and Terms of Service,and is used for providing, maintaining and improving the reCAPTCHA service and for general security purpose (it is not used for personalised advertising by Google)");
            }
        });
        buttonsignin=findViewById(R.id.signinbutton);
        buttonsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                startActivity(new Intent(SignInActivity.this, MainScreen.class));
            }
        });
        siginup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SwipeScreen.class));
            }
        });
        forgotpasswordextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this, "Forgot password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}