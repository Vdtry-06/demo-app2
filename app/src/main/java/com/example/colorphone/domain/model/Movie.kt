package com.example.colorphone.domain.model

data class Movie(
    val id: Long = 0,
    val title: String,
    val description: String,
    val posterUrl: String?,
    val duration: Int // Thời lượng phim (phút)
)
