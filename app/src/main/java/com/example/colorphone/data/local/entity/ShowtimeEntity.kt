package com.example.colorphone.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.colorphone.domain.model.Showtime

@Entity(
    tableName = "showtimes",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TheaterEntity::class,
            parentColumns = ["id"],
            childColumns = ["theaterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("movieId"), Index("theaterId")]
)
data class ShowtimeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val movieId: Long,
    val theaterId: Long,
    val startTime: Long,
    val endTime: Long,
    val roomName: String
) {
    fun toDomain() = Showtime(id, movieId, theaterId, startTime, endTime, roomName)
    companion object {
        fun fromDomain(showtime: Showtime) = ShowtimeEntity(
            id = showtime.id,
            movieId = showtime.movieId,
            theaterId = showtime.theaterId,
            startTime = showtime.startTime,
            endTime = showtime.endTime,
            roomName = showtime.roomName
        )
    }
}
