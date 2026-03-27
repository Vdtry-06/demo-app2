package com.example.colorphone.data.local

import androidx.room.*
import com.example.colorphone.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUser(userId: Long): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash")
    suspend fun login(email: String, passwordHash: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies")
    fun getAllMoviesFlow(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Long): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM theaters")
    suspend fun getAllTheaters(): List<TheaterEntity>

    @Query("SELECT * FROM theaters")
    fun getAllTheatersFlow(): Flow<List<TheaterEntity>>

    @Query("SELECT * FROM theaters WHERE id = :theaterId")
    suspend fun getTheaterById(theaterId: Long): TheaterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheaters(theaters: List<TheaterEntity>)

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    suspend fun getShowtimesForMovie(movieId: Long): List<ShowtimeEntity>

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    fun getShowtimesForMovieFlow(movieId: Long): Flow<List<ShowtimeEntity>>

    @Query("SELECT * FROM showtimes WHERE theaterId = :theaterId")
    suspend fun getShowtimesForTheater(theaterId: Long): List<ShowtimeEntity>

    @Query("SELECT * FROM showtimes WHERE theaterId = :theaterId")
    fun getShowtimesForTheaterFlow(theaterId: Long): Flow<List<ShowtimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShowtimes(showtimes: List<ShowtimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketEntity): Long

    @Query("SELECT * FROM tickets WHERE userId = :userId")
    suspend fun getTicketsForUser(userId: Long): List<TicketEntity>

    @Query("SELECT * FROM tickets WHERE userId = :userId")
    fun getTicketsForUserFlow(userId: Long): Flow<List<TicketEntity>>
}
