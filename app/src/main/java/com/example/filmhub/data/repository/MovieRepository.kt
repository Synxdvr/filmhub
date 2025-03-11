package com.example.filmhub.data.repository

import com.example.filmhub.data.model.Movie
import com.example.filmhub.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    suspend fun getPopularMovies(apiKey: String, page: Int): List<Movie>? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClient.instance.getPopularMovies(apiKey, page)
            if (response.isSuccessful) {
                response.body()?.results
            } else {
                null
            }
        }
    }
}