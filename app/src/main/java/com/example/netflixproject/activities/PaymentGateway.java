package com.example.netflixproject.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.camera2.params.BlackLevelPattern;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;
import com.example.netflixproject.mainscreens.MainScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Date;

public class PaymentGateway extends AppCompatActivity implements PaymentResultListener
{

    String PlanName,PlanCost,PlanCostFormat,email,password,fname,lname,cnumber;
    int color = Color.parseColor("#0B81DF");
    FirebaseAuth auth;
   // FirebaseDatabase database;
    FirebaseFirestore firebaseFirestore;
    Date vdate,date;
    FirebaseDatabase database;
    TextView one,firstname,lastname,number,continuemembership,costpayment,planpayment,changepayent;
    CheckBox checkbox;
    String TAG="payment Error";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        Checkout.preload(getApplicationContext());



        auth=FirebaseAuth.getInstance();
       database=FirebaseDatabase.getInstance();
       firebaseFirestore=FirebaseFirestore.getInstance();

        getSupportActionBar().hide();
        Intent i=getIntent();
        PlanName=i.getStringExtra("PlanName");
        PlanCost=i.getStringExtra("PlanCost");
        email=i.getStringExtra("EmailId");
        password=i.getStringExtra("Password");
        PlanCostFormat=i.getStringExtra("PlanCostFormat");
        //Toast.makeText(PaymentGateway.this, email+"\n"+password+"\n"+PlanName +" \n"+PlanCost+"\n "+PlanCostFormat, Toast.LENGTH_SHORT).show();

        one=findViewById(R.id.step3of3payment);
        firstname=findViewById(R.id.firstnamedittext);
        lastname=findViewById(R.id.lastnameedittext);
        number=findViewById(R.id.mobilenumberedittext);
        continuemembership=findViewById(R.id.continuemembershippayment);


        costpayment=findViewById(R.id.costpayment);
        planpayment=findViewById(R.id.planpayment);
        checkbox=findViewById(R.id.agreebtn);
        changepayent=findViewById(R.id.changepayment);
        TextView texxt=findViewById(R.id.textgateway);

        SpannableString st=new SpannableString("STEP 2 OF 3");
        StyleSpan boldspan=new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1=new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        one.setText(st);

        costpayment.setText(PlanCostFormat);
        planpayment.setText(PlanName);

        SpannableString s1=new SpannableString("By checking the checkbox below, you agree to our Terms of Use Privacy Statement, and that you are over 18. Netflix will automatically continue your membership and charge the monthly membership fee to your payment method until you cancel You may cancel at any time to avoid future charges");
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/legal/termofuse")));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(color);
            }
        };
        s1.setSpan(clickableSpan,49,61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/legal/privacy")));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(color);
            }

        };
        s1.setSpan(clickableSpan1,62,80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        texxt.setText(s1);
        texxt.setMovementMethod(LinkMovementMethod.getInstance());
        continuemembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox.isChecked())
                {
                    if(firstname.getText().toString().length()>3 && lastname.getText().toString().length()>3 && (firstname.getText().toString().matches("[a-z A-Z]+")) && (lastname.getText().toString().matches("[a-z A-Z]+")) && number.getText().toString().length()==10)
                        startPayment();
                    else
                    {
                        if(firstname.getText().toString().length()<3 ||!(firstname.getText().toString().matches("[a-z A-Z]+")))
                            firstname.setError("Enter Valid first name");
                        if(lastname.getText().toString().length()<3 ||!(lastname.getText().toString().matches("[a-z A-Z]+")))
                            lastname.setError("Enter Valid last name");
                        if(number.getText().toString().length()!=10)
                            number.setError("Enter a Valid contact number");

                    }
                }
                else
                    Toast.makeText(PaymentGateway.this, "Please Check the box", Toast.LENGTH_SHORT).show();

            }
        });

        changepayent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentGateway.this,ChooseYourPlan.class));
            }
        });



    }
    public void startPayment()
    {
        Checkout checkout=new Checkout();

        checkout.setKeyID("rzp_test_Bs3qw5iSflSOu2");//TODO

        final Activity activity=this;
        fname=firstname.getText().toString();
        lname=lastname.getText().toString();
        cnumber=number.getText().toString();

        String name=fname+lname;
        try {

            JSONObject options=new JSONObject();
            options.put("name",name);
            options.put("description","App Payment");
            options.put("currency","INR");
            String pay=PlanCost;
            double d=Double.parseDouble(pay);
            d=d*100;
            options.put("amount",d);
            options.put("prefill.email",email);
            options.put("prefill.contact",cnumber);
            checkout.open(activity,options);

        } catch (Exception e) {
            Log.e(TAG,"Error occured",e);
        }


    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();


        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {




                startActivity(new Intent(PaymentGateway.this,MainScreen.class));
            }
        });





    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();

    }
}