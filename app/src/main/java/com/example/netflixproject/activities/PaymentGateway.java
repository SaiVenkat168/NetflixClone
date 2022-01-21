package com.example.netflixproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;

public class PaymentGateway extends AppCompatActivity {

    String PlanName,PlanCost,PlanCostFormat,email,password;
    TextView firstname,lastname,number,continuemembership,costpayment,planpayment,changepayent;
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

        firstname=findViewById(R.id.firstnamedittext);
        lastname=findViewById(R.id.lastnameedittext);
        number=findViewById(R.id.mobilenumberedittext);
        continuemembership=findViewById(R.id.continuemembershippayment);
        costpayment=findViewById(R.id.costpayment);
        planpayment=findViewById(R.id.planpayment);
        checkbox=findViewById(R.id.checkbox);
        changepayent=findViewById(R.id.changepayment);

        costpayment.setText(PlanCostFormat);
        planpayment.setText(PlanName);
        continuemembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox.isChecked())
                    Toast.makeText(PaymentGateway.this, "Please Check the box", Toast.LENGTH_SHORT).show();
                else
                    startActivity(new Intent(PaymentGateway.this,Payment.class));
            }
        });



    }
}