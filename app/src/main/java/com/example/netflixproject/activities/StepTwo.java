package com.example.netflixproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;

import java.util.Timer;
import java.util.TimerTask;

public class StepTwo extends AppCompatActivity {
    TextView signin,signinbtn,continuebtnsteptwo,one;
    ProgressBar progressbar;
    TextView useremail,userpassword;
    String PlanName,PlanCost,PlanCostFormat,email,password;
    static int time=1500,c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two);
        getSupportActionBar().hide();
        Intent i=getIntent();
        PlanName=i.getStringExtra("PlanName");
        PlanCost=i.getStringExtra("PlanCost");
        PlanCostFormat=i.getStringExtra("PlanCostFormat");
        Toast.makeText(StepTwo.this, PlanName +" "+PlanCost+" "+PlanCostFormat, Toast.LENGTH_SHORT).show();
        signinbtn=findViewById(R.id.siginbtnsteptwo);
        useremail=findViewById(R.id.emailedittextsteptwo);
        userpassword=findViewById(R.id.passwordedittextsteptwo);
        progressbar=findViewById(R.id.pbsteptwo);
        progressbar.setVisibility(View.GONE);

        signin=findViewById(R.id.signintextviewstepone);
        one=findViewById(R.id.steptwoofthree);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StepTwo.this,SignInActivity.class));
            }
        });
        continuebtnsteptwo=findViewById(R.id.siginbtnsteptwo);

        continuebtnsteptwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=useremail.getText().toString();
                password=userpassword.getText().toString();
                progressbar.setVisibility(View.VISIBLE);
              Intent  i = new Intent(StepTwo.this, StepThree.class);
                i.putExtra("PlanName",PlanName);
                i.putExtra("PlanCost",PlanCost);
                i.putExtra("PlanCostFormat",PlanCostFormat);
                i.putExtra("EmailId",email);
                i.putExtra("Password",password);
                startActivity(i);
            }
        });

        SpannableString st=new SpannableString("STEP 2 OF 3");
        StyleSpan boldspan=new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1=new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        one.setText(st);


    }
    public void progress()
    {
        final Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                c++;
                progressbar.setProgress(c);
                if(c==time)
                    timer.cancel();
            }
        };
        timer.schedule(timerTask,0,100);
    }



}