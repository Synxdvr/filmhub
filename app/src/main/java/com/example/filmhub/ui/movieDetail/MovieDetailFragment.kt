package com.example.filmhub.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.filmhub.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie
        binding.tvMovieTitle.text = movie.title
        binding.tvMovieDescription.text = movie.overview
        binding.tvMovieRating.text = "${movie.voteAverage}"
        Glide.with(binding.ivMovieBackdrop.context)
            .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}").into(binding.ivMovieBackdrop)
    }
}
