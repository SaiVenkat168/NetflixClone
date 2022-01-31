package com.example.netflixproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.netflixproject.R;
import com.example.netflixproject.mainscreens.MainScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    static int time=4500,count=0;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference reference;
    Date today,validdate;
    String Uid;
    boolean t=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Calendar c=Calendar.getInstance();
        today=c.getTime();


        progressBar=findViewById(R.id.progressbar);
        progress();
        start();
    }
    public void progress()
    {
        final Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                find();
                count++;
                progressBar.setProgress(count);
                if(count==time)
                    timer.cancel();


            }
        };
        timer.schedule(timerTask,0,100);
    }
    public void find()
    {
        if(t)
        {

            if(auth.getCurrentUser()!=null)
            {
                t=false;
                Uid= auth.getCurrentUser().getUid();
                reference=firebaseFirestore.collection("Users").document(Uid);
                reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        validdate=documentSnapshot.getDate("Valid_date");
                        if(validdate.compareTo(today)>=0)
                        {
                            startActivity(new Intent(SplashScreen.this, MainScreen.class));
                            Toast.makeText(SplashScreen.this, "Login is Successful", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(SplashScreen.this, "Plan Expired", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SplashScreen.this,PaymentOverdue.class));
                        }
                    }
                });
            }
        }
    }
    public void start()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (t)
                startActivity(new Intent(SplashScreen.this, SignInActivity.class));
                finish();
            }
        },time);

    }
}