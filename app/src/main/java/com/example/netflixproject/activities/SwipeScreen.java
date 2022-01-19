package com.example.netflixproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.netflixproject.R;
import com.example.netflixproject.adaptar.ViewPagerAdapter;

public class SwipeScreen extends AppCompatActivity {

    TextView signin,help,privacy,getstarted;
    ViewPager viewPagerswipe;
    private int dotscount;
    LinearLayout sliderdots;
    private ImageView dots[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_screen);
        getSupportActionBar().hide();
        signin=findViewById(R.id.signintextview);
        help=findViewById(R.id.helptextview);
        privacy=findViewById(R.id.privacytextview);
        getstarted=findViewById(R.id.btngetstarted);
        viewPagerswipe=findViewById(R.id.viewpagerswipescreen);
        sliderdots=findViewById(R.id.sliderdots);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);

        viewPagerswipe.setAdapter(viewPagerAdapter);
        dotscount=viewPagerAdapter.getCount();
        dots=new ImageView[dotscount];
        for(int i=0;i<dotscount;i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactive_dots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            sliderdots.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));


        viewPagerswipe.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position)
                {
                    for(int i=0;i<dotscount;i++)
                    {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dots));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SwipeScreen.this,SignInActivity.class));
            }
        });


        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/en/node/100628")));
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/en/")));
            }
        });
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SwipeScreen.this, StepOne.class));
            }
        });


    }
}