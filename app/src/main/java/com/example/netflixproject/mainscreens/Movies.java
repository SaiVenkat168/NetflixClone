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
import android.widget.ImageView;

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

public class Movies extends AppCompatActivity {
    ImageView logo;
    BottomNavigationView bottomNavigationView;
    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainrecycler;
    List<AllCategory> allCategoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        getSupportActionBar().hide();
        logo=findViewById(R.id.movnetflixlogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Movies.this,MainScreen.class));
            }
        });

        bottomNavigationView=findViewById(R.id.navmovie);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.homeicon:
                        startActivity(new Intent(Movies.this,MainScreen.class));
                        break;
                    case R.id.searchicon:
                        startActivity(new Intent(Movies.this,Search.class));
                        break;
                    case R.id.settingsicon:
                        startActivity(new Intent(Movies.this,Settings.class));
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
        mainrecycler=findViewById(R.id.MoviesRecyclerView);
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