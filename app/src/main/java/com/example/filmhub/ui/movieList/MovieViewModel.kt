package com.example.filmhub.ui.movieList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmhub.data.model.Movie
import com.example.filmhub.data.repository.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    val movies = MutableLiveData<List<Movie>?>()
    var currentPage = 1
    var totalPages = Int.MAX_VALUE
    var isLoading = false

    private val repository = MovieRepository()

    fun fetchPopularMovies(apiKey: String) {
        if (isLoading || currentPage > totalPages) return

        isLoading = true
        viewModelScope.launch {
            delay(2000)
            val newMovies = repository.getPopularMovies(apiKey, currentPage)
            newMovies?.let {
                val updatedList = if (currentPage == 1) {
                    it.toMutableList()
                } else {
                    (movies.value ?: emptyList()).toMutableList().apply { addAll(it) }
                }
                movies.postValue(updatedList)
            }
            isLoading = false
        }
    }

    fun hasMorePages() = currentPage < totalPages

    fun loadNextPage(apiKey: String) {
        if (hasMorePages() && !isLoading) {
            currentPage++
            fetchPopularMovies(apiKey)
        }
    }
}
