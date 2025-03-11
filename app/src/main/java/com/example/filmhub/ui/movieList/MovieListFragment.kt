package com.example.filmhub.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        binding.srlMovieList.setOnRefreshListener {
            movieViewModel.currentPage = 1
            movieViewModel.fetchPopularMovies(APIConfig.API_KEY)
        }

        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
            binding.piMovieListLoader.visibility = View.GONE
            binding.srlMovieList.isRefreshing = false

            if (!movies.isNullOrEmpty()) {
                binding.rvMovieList.visibility = View.VISIBLE
                binding.tvErrorText.visibility = View.GONE
                movieAdapter.submitList(movies.toList())
            } else {
                binding.rvMovieList.visibility = View.GONE
                binding.tvErrorText.visibility = View.VISIBLE
                binding.tvErrorText.text = "No movies available. Please try again later."
            }
        }

        binding.piMovieListLoader.visibility = View.VISIBLE
        movieViewModel.fetchPopularMovies(APIConfig.API_KEY)

        binding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!movieViewModel.isLoading && movieViewModel.hasMorePages()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        movieViewModel.loadNextPage(APIConfig.API_KEY)
                    }
                }
            }
        })
    }
}
