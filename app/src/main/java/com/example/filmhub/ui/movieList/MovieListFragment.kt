package com.example.filmhub.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmhub.adapter.MovieAdapter
import com.example.filmhub.databinding.FragmentMovieListBinding
import com.example.filmhub.utils.APIConfig

class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var movieAdapter: MovieAdapter
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter { selectedMovie ->
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                selectedMovie
            )
            findNavController().navigate(action)
        }
        binding.rvMovieList.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = movieAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            movieViewModel.fetchPopularMovies(APIConfig.API_KEY)
        }

        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
            binding.progressBar.visibility = View.GONE
            binding.swipeRefreshLayout.isRefreshing = false

            if (!movies.isNullOrEmpty()) {
                binding.rvMovieList.visibility = View.VISIBLE
                binding.errorText.visibility = View.GONE
                movieAdapter.submitList(movies)
            } else {
                binding.rvMovieList.visibility = View.GONE
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = "No movies available. Please try again later."
            }
        }


        binding.progressBar.visibility = View.VISIBLE
        movieViewModel.fetchPopularMovies(APIConfig.API_KEY)
    }
}
