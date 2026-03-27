package com.example.colorphone.ui.showtime

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorphone.domain.model.Showtime
import com.example.colorphone.domain.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ShowtimeViewModel @Inject constructor(
    private val cinemaRepository: CinemaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])
    private val theaterId: Long = checkNotNull(savedStateHandle["theaterId"])

    val showtimes: StateFlow<List<Showtime>> = cinemaRepository.getShowtimesForMovieAndTheaterFlow(movieId, theaterId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
