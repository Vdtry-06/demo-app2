package com.example.colorphone.domain.model

data class Ticket(
    val id: Long = 0,
    val showtimeId: Long,
    val userId: Long,
    val seatNumber: String,
    val bookingTime: Long,
    val price: Double
)
