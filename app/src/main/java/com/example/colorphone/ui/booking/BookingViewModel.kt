package com.example.colorphone.ui.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorphone.domain.repository.CinemaRepository
import com.example.colorphone.util.SessionManager
import com.example.colorphone.ui.booking.model.SeatStatus
import com.example.colorphone.ui.booking.model.SeatUiModel
import com.example.colorphone.domain.model.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: CinemaRepository,
    private val sessionManager: SessionManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val showtimeId: Long = savedStateHandle.get<Long>("showtimeId") ?: -1L

    private val _seats = MutableStateFlow<List<SeatUiModel>>(emptyList())
    val seats: StateFlow<List<SeatUiModel>> = _seats.asStateFlow()

    private val _bookingSuccess = MutableSharedFlow<Unit>()
    val bookingSuccess: SharedFlow<Unit> = _bookingSuccess

    init {
        loadSeats()
    }

    private fun loadSeats() {
        if (showtimeId == -1L) return

        viewModelScope.launch {
            repository.getTicketsForShowtimeFlow(showtimeId).collectLatest { bookedTickets ->
                val bookedSeatNumbers = bookedTickets.map { it.seatNumber }.toSet()
                
                val defaultSeats = generateDefaultSeats()
                val updatedSeats = defaultSeats.map { seat ->
                    if (bookedSeatNumbers.contains(seat.seatNumber)) {
                        seat.copy(status = SeatStatus.BOOKED)
                    } else {
                        seat
                    }
                }
                _seats.value = updatedSeats
            }
        }
    }

    private fun generateDefaultSeats(): List<SeatUiModel> {
        val rows = listOf("A", "B", "C")
        val seats = mutableListOf<SeatUiModel>()
        for (row in rows) {
            for (i in 1..10) {
                seats.add(
                    SeatUiModel(
                        seatNumber = "$row$i",
                        status = SeatStatus.AVAILABLE,
                        price = 100000.0 // Default price
                    )
                )
            }
        }
        return seats
    }

    fun onSeatClicked(seat: SeatUiModel) {
        val currentList = _seats.value.toMutableList()
        val index = currentList.indexOfFirst { it.seatNumber == seat.seatNumber }
        if (index != -1) {
            val currentStatus = currentList[index].status
            if (currentStatus == SeatStatus.AVAILABLE) {
                currentList[index] = currentList[index].copy(status = SeatStatus.SELECTED)
            } else if (currentStatus == SeatStatus.SELECTED) {
                currentList[index] = currentList[index].copy(status = SeatStatus.AVAILABLE)
            }
            _seats.value = currentList
        }
    }

    fun bookSelected() {
        val selectedSeats = _seats.value.filter { it.status == SeatStatus.SELECTED }
        if (selectedSeats.isEmpty()) return
        
        val userId = sessionManager.getUserId()
        if (userId == -1L) return

        viewModelScope.launch {
            selectedSeats.forEach { seat ->
                val ticket = Ticket(
                    showtimeId = showtimeId,
                    userId = userId,
                    seatNumber = seat.seatNumber,
                    bookingTime = System.currentTimeMillis(),
                    price = seat.price
                )
                repository.bookTicket(ticket)
            }
            _bookingSuccess.emit(Unit)
        }
    }
}
