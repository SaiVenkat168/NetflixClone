package com.example.netflixproject.mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.netflixproject.R;
import com.example.netflixproject.adaptar.MainRecyclerAdapter;
import com.example.netflixproject.modals.AllCategory;
import com.example.netflixproject.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainScreen extends AppCompatActivity {

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainrecycler;
    List<AllCategory> allCategoryList;

    TextView movies,tvseries;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getSupportActionBar().hide();
        movies=findViewById(R.id.moviesseriestooltext);
        tvseries=findViewById(R.id.tvseriestooltext);

        //mainrecycler=findViewById(R.id.MainRecyclerView);

        movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this,Movies.class));
            }
        });
        tvseries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this,TvSeries.class));
            }
        });

        bottomNavigationView=findViewById(R.id.navmain);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.homeicon:
                        break;
                    case R.id.searchicon:
                        startActivity(new Intent(MainScreen.this,Search.class));
                        break;
                    case R.id.settingsicon:
                        startActivity(new Intent(MainScreen.this,Settings.class));
                        break;
                }
                return false;
            }
        });

        allCategoryList=new ArrayList<>();
        getAllMovieData();

    }
    public  void setMainrecycler(List<AllCategory> allCategoryList)
    {
        mainrecycler=findViewById(R.id.MainRecyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mainrecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter= new MainRecyclerAdapter(this,allCategoryList);
        mainrecycler.setAdapter(mainRecyclerAdapter);
    }

    private void getAllMovieData()
    {
        CompositeDisposable compositeDisposable= new CompositeDisposable();
        compositeDisposable.add(RetrofitClient.getRetrofitClient().getAllCategoryMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<AllCategory>>() {
                    @Override
                    public void onNext(@NonNull List<AllCategory> allCategoryList) {
                        setMainrecycler(allCategoryList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

}