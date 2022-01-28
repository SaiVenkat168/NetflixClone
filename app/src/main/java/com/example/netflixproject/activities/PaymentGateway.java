package com.example.netflixproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;
import com.example.netflixproject.mainscreens.MainScreen;

public class PaymentGateway extends AppCompatActivity {

    String PlanName,PlanCost,PlanCostFormat,email,password;
    TextView one,firstname,lastname,number,continuemembership,costpayment,planpayment,changepayent;
    CheckBox checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        getSupportActionBar().hide();
        Intent i=getIntent();
        PlanName=i.getStringExtra("PlanName");
        PlanCost=i.getStringExtra("PlanCost");
        email=i.getStringExtra("EmailId");
        password=i.getStringExtra("Password");
        PlanCostFormat=i.getStringExtra("PlanCostFormat");
        Toast.makeText(PaymentGateway.this, email+"\n"+password+"\n"+PlanName +" \n"+PlanCost+"\n "+PlanCostFormat, Toast.LENGTH_SHORT).show();

        one=findViewById(R.id.step3of3payment);
        firstname=findViewById(R.id.firstnamedittext);
        lastname=findViewById(R.id.lastnameedittext);
        number=findViewById(R.id.mobilenumberedittext);
        continuemembership=findViewById(R.id.continuemembershippayment);
        costpayment=findViewById(R.id.costpayment);
        planpayment=findViewById(R.id.planpayment);
        checkbox=findViewById(R.id.agreebtn);
        changepayent=findViewById(R.id.changepayment);

        SpannableString st=new SpannableString("STEP 2 OF 3");
        StyleSpan boldspan=new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1=new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        one.setText(st);

        costpayment.setText(PlanCostFormat);
        planpayment.setText(PlanName);
        continuemembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkbox.isChecked())
                {
                    Toast.makeText(PaymentGateway.this, "Please Check the box", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(PaymentGateway.this, Payment.class));
                }


            }
        });

        changepayent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentGateway.this,ChooseYourPlan.class));
            }
        });

        SpannableString s1=new SpannableString("By checking the checkbox below, you agree to our Terms of Use Privacy Statement, and that you are over 18. Netflix will automatically continue your membership and charge the monthly membership fee to your payment method until you cancel You may cancel at any time to avoid future charges");
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/legal/termofuse")));
            }
        };
        ClickableSpan clickableSpan1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/legal/privacy")));
            }
        };
        s1.setSpan(clickableSpan,49,61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s1.setSpan(clickableSpan,63,80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);




    }
}