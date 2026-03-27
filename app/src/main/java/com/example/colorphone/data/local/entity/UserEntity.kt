package com.example.colorphone.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.colorphone.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val email: String,
    val passwordHash: String
) {
    fun toDomain() = User(id, username, email, passwordHash)
    companion object {
        fun fromDomain(user: User) = UserEntity(
            id = if (user.id == 0L) 0 else user.id,
            username = user.username,
            email = user.email,
            passwordHash = user.passwordHash
        )
    }
}
