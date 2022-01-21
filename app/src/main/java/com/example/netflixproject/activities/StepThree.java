package com.example.netflixproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;

public class StepThree extends AppCompatActivity {
    TextView signout;
    String PlanName,PlanCost,PlanCostFormat,email,password;
    LinearLayout paymentlinearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_three);
        getSupportActionBar().hide();
        Intent i=getIntent();
        PlanName=i.getStringExtra("PlanName");
        PlanCost=i.getStringExtra("PlanCost");
        email=i.getStringExtra("EmailId");
        password=i.getStringExtra("Password");
        PlanCostFormat=i.getStringExtra("PlanCostFormat");
        Toast.makeText(StepThree.this, PlanName +" "+PlanCost+" "+PlanCostFormat, Toast.LENGTH_SHORT).show();

        TextView one=findViewById(R.id.step3of3);
        paymentlinearlayout=findViewById(R.id.paymentlinearlayout);
        signout=findViewById(R.id.signoutstepthree);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        paymentlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  i = new Intent(StepThree.this, PaymentGateway.class);
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
}