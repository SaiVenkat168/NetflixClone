package com.example.netflixproject.mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.netflixproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {
    ImageView logo;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        logo=findViewById(R.id.setnetflixlogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,MainScreen.class));
            }
        });

        bottomNavigationView=findViewById(R.id.navset);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.homeicon:
                        startActivity(new Intent(Settings.this,MainScreen.class));
                        break;
                    case R.id.searchicon:
                        startActivity(new Intent(Settings.this,Search.class));
                        break;
                    case R.id.settingsicon:
                        //startActivity(new Intent(MainScreen.this,Settings.class));
                        break;
                }
                return false;
            }
        });


    }
}