package com.example.colorphone.domain.model

data class Showtime(
    val id: Long = 0,
    val movieId: Long,
    val theaterId: Long,
    val startTime: Long,
    val endTime: Long,
    val roomName: String
)
