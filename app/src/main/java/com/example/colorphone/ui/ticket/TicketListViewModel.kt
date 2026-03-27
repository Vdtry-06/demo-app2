package com.example.colorphone.ui.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorphone.domain.model.Ticket
import com.example.colorphone.domain.repository.CinemaRepository
import com.example.colorphone.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TicketListViewModel @Inject constructor(
    private val repository: CinemaRepository,
    sessionManager: SessionManager
) : ViewModel() {

    private val userId = sessionManager.getUserId()

    val tickets: StateFlow<List<Ticket>> = repository.getTicketsForUserFlow(userId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
