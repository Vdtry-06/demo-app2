package com.example.colorphone.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.colorphone.domain.model.Theater

@Entity(tableName = "theaters")
data class TheaterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val address: String
) {
    fun toDomain() = Theater(id, name, address)
    companion object {
        fun fromDomain(theater: Theater) = TheaterEntity(
            id = theater.id,
            name = theater.name,
            address = theater.address
        )
    }
}
