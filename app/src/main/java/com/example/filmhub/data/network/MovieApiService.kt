package com.example.filmhub.data.network

import com.example.filmhub.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<ApiResponse>
}