package com.example.netflixproject.mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.netflixproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        bottomNavigationView=findViewById(R.id.navsearch);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.homeicon:
                        startActivity(new Intent(Search.this,MainScreen.class));
                        break;
                    case R.id.searchicon:
                        //startActivity(new Intent(Search.this,Search.class));
                        break;
                    case R.id.settingsicon:
                        startActivity(new Intent(Search.this,Settings.class));
                        break;
                }
                return false;
            }
        });

    }
}