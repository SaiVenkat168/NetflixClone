package com.example.netflixproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.netflixproject.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    static int time=5000,c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
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
                c++;
                progressBar.setProgress(c);
                if(c==time)
                    timer.cancel();
            }
        };
        timer.schedule(timerTask,0,100);
    }
    public void start()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, SignInActivity.class));
                finish();
            }
        },time);

    }
}