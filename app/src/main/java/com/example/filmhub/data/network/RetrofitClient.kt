package com.example.filmhub.data.network

import com.example.filmhub.utils.APIConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val instance: MovieApiService by lazy {
        Retrofit.Builder().baseUrl(APIConfig.TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MovieApiService::class.java)
    }
}