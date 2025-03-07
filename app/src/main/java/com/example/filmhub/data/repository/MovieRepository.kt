package com.example.filmhub.data.repository

import com.example.filmhub.data.model.Movie
import com.example.filmhub.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    suspend fun getPopularMovies(apiKey: String): List<Movie>? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClient.instance.getPopularMovies(apiKey)
            if (response.isSuccessful) {
                response.body()?.results
            } else {
                null
            }
        }
    }
}