package com.example.netflixproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.netflixproject.R;

public class ChooseYourPlan extends AppCompatActivity
{
    CardView ba,sta,pre;
    TextView continuebtn,sigin;
    RadioButton radiobasic,radiostandard,radiopremium;
    String planname,plancost,planformatcost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_plan);
        getSupportActionBar().hide();
        sigin=findViewById(R.id.signintextviewstepone);
        ba=findViewById(R.id.bas);
        sta=findViewById(R.id.sta);
        pre=findViewById(R.id.pre);
        continuebtn=findViewById(R.id.continuebtnchooseplan);

        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseYourPlan.this,SignInActivity.class));
            }
        });

        radiobasic=findViewById(R.id.radiobtnforbasic);
        radiostandard=findViewById(R.id.radiobtnforstandard);
        radiopremium=findViewById(R.id.radiobtnforpremium);

        radiobasic.setOnCheckedChangeListener(new Radio_check());
        radiobasic.setChecked(true);

        radiostandard.setOnCheckedChangeListener(new Radio_check());

        radiopremium.setOnCheckedChangeListener(new Radio_check());


        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChooseYourPlan.this,FinishUpAccount.class);
                i.putExtra("PlanName",planname);
                i.putExtra("PlanCost",plancost);
                i.putExtra("PlanCostFormat",planformatcost);
                startActivity(i);
            }
        });

    }
    class Radio_check implements CompoundButton.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked)
            {
                if(compoundButton.getId()==R.id.radiobtnforbasic)
                {
                    planname="Basic";
                    plancost="349";
                    planformatcost="₹ 349/month";
                    radiostandard.setChecked(false);
                    radiopremium.setChecked(false);
                }
                if(compoundButton.getId()==R.id.radiobtnforstandard)
                {
                    planname="Standard";
                    plancost="649";
                    planformatcost="₹ 649/month";
                    radiobasic.setChecked(false);
                    radiopremium.setChecked(false);
                }
                if(compoundButton.getId()==R.id.radiobtnforpremium)
                {
                    planname="Premium";
                    plancost="999";
                    planformatcost="₹ 999/month";
                    radiostandard.setChecked(false);
                    radiobasic.setChecked(false);
                }
            }

        }
    }
}