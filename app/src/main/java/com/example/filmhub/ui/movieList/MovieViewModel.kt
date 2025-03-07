package com.example.filmhub.ui.movieList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmhub.data.model.Movie
import com.example.filmhub.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    val movies = MutableLiveData<List<Movie>?>()
    private val repository = MovieRepository()

    fun fetchPopularMovies(apiKey: String) {
        viewModelScope.launch {
            val movieList = repository.getPopularMovies(apiKey)
            movies.postValue(movieList)
        }
    }
}
