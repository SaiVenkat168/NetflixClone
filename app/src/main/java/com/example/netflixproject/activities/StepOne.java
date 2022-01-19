package com.example.netflixproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.example.netflixproject.R;

public class StepOne extends AppCompatActivity {
    TextView btnplans,signin,one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one);
        getSupportActionBar().hide();
        signin=findViewById(R.id.signintextviewstepone);
        btnplans=findViewById(R.id.plans);
        one=findViewById(R.id.one);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StepOne.this,SignInActivity.class));
            }
        });
        btnplans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StepOne.this,ChooseYourPlan.class));

            }
        });

        SpannableString st=new SpannableString("STEP 1 OF 3");
        StyleSpan boldspan=new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1=new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        one.setText(st);


    }
}