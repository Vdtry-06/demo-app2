package com.example.colorphone.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.colorphone.domain.model.Ticket

@Entity(
    tableName = "tickets",
    foreignKeys = [
        ForeignKey(
            entity = ShowtimeEntity::class,
            parentColumns = ["id"],
            childColumns = ["showtimeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("showtimeId"), Index("userId")]
)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val showtimeId: Long,
    val userId: Long,
    val seatNumber: String,
    val bookingTime: Long,
    val price: Double
) {
    fun toDomain() = Ticket(id, showtimeId, userId, seatNumber, bookingTime, price)
    companion object {
        fun fromDomain(ticket: Ticket) = TicketEntity(
            id = ticket.id,
            showtimeId = ticket.showtimeId,
            userId = ticket.userId,
            seatNumber = ticket.seatNumber,
            bookingTime = ticket.bookingTime,
            price = ticket.price
        )
    }
}
