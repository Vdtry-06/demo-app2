package com.example.colorphone.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.colorphone.R
import com.example.colorphone.databinding.FragmentMovieBinding
import com.example.colorphone.ui.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::inflate) {

    private val viewModel: MovieViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy { 
        MovieAdapter { movie ->
            val action = MovieFragmentDirections.actionMovieFragmentToTheatreFragment(movie.id)
            navController.navigate(action)
        } 
    }

    override fun setupView() {
        super.setupView()
        binding.rvMovies.adapter = movieAdapter
    }

    override fun setUpListener() {
        super.setUpListener()
        binding.btnLogin.setOnClickListener {
            navController.navigate(R.id.loginFragment)
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collectLatest { movies ->
                    movieAdapter.submitList(movies)
                }
            }
        }
    }
}
