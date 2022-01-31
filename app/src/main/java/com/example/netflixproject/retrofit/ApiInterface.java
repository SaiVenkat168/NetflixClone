package com.example.netflixproject.retrofit;


import static com.example.netflixproject.retrofit.RetrofitClient.BASE_URL;

import com.example.netflixproject.modals.AllCategory;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET(BASE_URL)
    Observable<List<AllCategory>> getAllCategoryMovies();
}
