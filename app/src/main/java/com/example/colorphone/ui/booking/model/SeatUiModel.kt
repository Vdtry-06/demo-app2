package com.example.colorphone.ui.booking.model

enum class SeatStatus {
    AVAILABLE,
    BOOKED,
    SELECTED
}

data class SeatUiModel(
    val seatNumber: String,
    val status: SeatStatus,
    val price: Double
)
