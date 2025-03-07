package com.example.filmhub.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val TMDB_URL = "https://api.themoviedb.org/3/"

    val instance: MovieApiService by lazy {
        Retrofit.Builder().baseUrl(TMDB_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieApiService::class.java)
    }
}