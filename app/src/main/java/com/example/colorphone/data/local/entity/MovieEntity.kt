package com.example.colorphone.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.colorphone.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val posterUrl: String?,
    val duration: Int
) {
    fun toDomain() = Movie(id, title, description, posterUrl, duration)
    companion object {
        fun fromDomain(movie: Movie) = MovieEntity(
            id = movie.id,
            title = movie.title,
            description = movie.description,
            posterUrl = movie.posterUrl,
            duration = movie.duration
        )
    }
}
