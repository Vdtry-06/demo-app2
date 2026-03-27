package com.example.colorphone.ui.theatre

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorphone.domain.model.Theater
import com.example.colorphone.domain.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TheatreViewModel @Inject constructor(
    private val cinemaRepository: CinemaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])

    val theatres: StateFlow<List<Theater>> = cinemaRepository.getTheatersForMovieFlow(movieId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
