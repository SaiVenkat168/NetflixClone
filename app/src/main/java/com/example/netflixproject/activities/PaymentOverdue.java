package com.example.netflixproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;
import com.example.netflixproject.mainscreens.MainScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentOverdue extends AppCompatActivity implements PaymentResultListener
{
    //CardView ba,sta,pre;
    TextView continuebtn,sigin;
    RadioButton radiobasic,radiostandard,radiopremium;
    String planname,plancost,planformatcost,TAG="Error";
    String firstname,lastname;
    Date today,validdate;
    FirebaseAuth auth;
    DocumentReference documentReference;
    // FirebaseDatabase database;
    FirebaseFirestore firebaseFirestore;
    String fname,lname,number,mail,Userid,name;
    String pay;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_overdue);
        getSupportActionBar().hide();
        sigin=findViewById(R.id.signintextviewstepone);
//        ba=findViewById(R.id.bas);
//        sta=findViewById(R.id.sta);
//        pre=findViewById(R.id.pre);
        Checkout.preload(getApplicationContext());

        Calendar c=Calendar.getInstance();
        today=c.getTime();
        c.add(Calendar.MONTH,1);
        validdate=c.getTime();

        firebaseFirestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();


        continuebtn=findViewById(R.id.paybtn);

        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentOverdue.this,SignInActivity.class));
            }
        });

        radiobasic=findViewById(R.id.radiobtnforbasic1);
        radiostandard=findViewById(R.id.radiobtnforstandard1);
        radiopremium=findViewById(R.id.radiobtnforpremium1);

        radiobasic.setOnCheckedChangeListener(new Radio_checkk());
        radiopremium.setChecked(true);
        setPay("999");

        radiostandard.setOnCheckedChangeListener(new Radio_checkk());

        radiopremium.setOnCheckedChangeListener(new Radio_checkk());


        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startPayment();
            }
        });

    }
    class Radio_checkk implements CompoundButton.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked)
            {
                if(compoundButton.getId()==R.id.radiobtnforbasic1)
                {
                    planname="Basic";
                    plancost="349";
                    planformatcost="₹ 349/month";
                    setPay(plancost);
                    radiostandard.setChecked(false);
                    radiopremium.setChecked(false);
                }
                if(compoundButton.getId()==R.id.radiobtnforstandard1)
                {
                    planname="Standard";
                    plancost="649";
                    setPay(plancost);
                    planformatcost="₹ 649/month";
                    radiobasic.setChecked(false);
                    radiopremium.setChecked(false);
                }
                if(compoundButton.getId()==R.id.radiobtnforpremium1)
                {
                    planname="Premium";
                    plancost="999";
                    setPay(plancost);
                    planformatcost="₹ 999/month";
                    radiostandard.setChecked(false);
                    radiobasic.setChecked(false);
                }
            }

        }
    }


    public void startPayment()
    {
        Checkout checkout=new Checkout();
        checkout.setKeyID("rzp_test_Bs3qw5iSflSOu2");
        final Activity activity=this;
        Userid=auth.getCurrentUser().getUid();
        documentReference=firebaseFirestore.collection("Users").document(Userid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {

                fname=documentSnapshot.getString("Firts_name");
                lname=documentSnapshot.getString("Last_name");
                setFirstname(fname);
                setLastname(lname);
                Toast.makeText(getApplicationContext(), fname+"\n"+lname+"\n"+mail+"\n"+number, Toast.LENGTH_SHORT).show();
                number=documentSnapshot.getString("Contact_number");
                mail=documentSnapshot.getString("Email");
            }
        });
        try {
            name=getFirstname()+getLastname();
            JSONObject options=new JSONObject();
            //options.put("name",mail);
            options.put("description","App Payment");
            options.put("currency","INR");
            double d=Double.parseDouble(getPay());
            d=d*100;
            options.put("amount",d);
            options.put("prefill.email",mail);
            options.put("prefill.contact",number);
            checkout.open(activity,options);

        } catch (Exception e) {
            Log.e(TAG,"Error occured",e);
        }


    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();



        DocumentReference documentReference=firebaseFirestore.collection("Users").document(Userid);
        Map<String,Object> user=new HashMap<>();
        user.put("Email",mail);
        user.put("Firts_name",fname);
        user.put("Last_name",lname);
        user.put("Plan_cost",getPay());
        user.put("Contact_number",number);
        //user.put("Register_date",date.toString());
        user.put("Valid_date",validdate);
        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {
                startActivity(new Intent(PaymentOverdue.this,MainScreen.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error in storing values", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();

    }


    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}