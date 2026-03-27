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

    // Vẫn giữ movieId nếu cần dùng cho các bước tiếp theo (ví dụ: đặt vé cho phim này tại rạp đã chọn)
    private val movieId: Long = checkNotNull(savedStateHandle["movieId"])

    val theatres: StateFlow<List<Theater>> = cinemaRepository.getAllTheatersFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
