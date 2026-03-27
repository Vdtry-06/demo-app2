package com.example.colorphone.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorphone.domain.model.Movie
import com.example.colorphone.domain.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val cinemaRepository: CinemaRepository
) : ViewModel() {

    val movies: StateFlow<List<Movie>> = cinemaRepository.getAllMoviesFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
