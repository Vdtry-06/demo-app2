package com.example.colorphone.domain.repository

import com.example.colorphone.data.local.CinemaDao
import com.example.colorphone.data.local.entity.MovieEntity
import com.example.colorphone.data.local.entity.TheaterEntity
import com.example.colorphone.data.local.entity.TicketEntity
import com.example.colorphone.data.local.entity.UserEntity
import com.example.colorphone.domain.model.Movie
import com.example.colorphone.domain.model.Showtime
import com.example.colorphone.domain.model.Theater
import com.example.colorphone.domain.model.Ticket
import com.example.colorphone.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class CinemaRepository @Inject constructor(
    private val cinemaDao: CinemaDao
) {
    suspend fun getUser(userId: Long): User? = cinemaDao.getUser(userId)?.toDomain()

    suspend fun login(email: String, passwordHash: String): User? =
        cinemaDao.login(email, passwordHash)?.toDomain()

    suspend fun register(user: User): Long =
        cinemaDao.insertUser(UserEntity.fromDomain(user))

    suspend fun getAllMovies(): List<Movie> =
        cinemaDao.getAllMovies().map { it.toDomain() }

    fun getAllMoviesFlow(): Flow<List<Movie>> =
        cinemaDao.getAllMoviesFlow().map { list -> list.map { it.toDomain() } }

    suspend fun getMovieById(movieId: Long): Movie? =
        cinemaDao.getMovieById(movieId)?.toDomain()

    suspend fun getAllTheaters(): List<Theater> =
        cinemaDao.getAllTheaters().map { it.toDomain() }

    fun getAllTheatersFlow(): Flow<List<Theater>> =
        cinemaDao.getAllTheatersFlow().map { list -> list.map { it.toDomain() } }

    suspend fun getTheaterById(theaterId: Long): Theater? =
        cinemaDao.getTheaterById(theaterId)?.toDomain()

    suspend fun getShowtimesForMovie(movieId: Long): List<Showtime> =
        cinemaDao.getShowtimesForMovie(movieId).map { it.toDomain() }

    fun getShowtimesForMovieFlow(movieId: Long): Flow<List<Showtime>> =
        cinemaDao.getShowtimesForMovieFlow(movieId).map { list -> list.map { it.toDomain() } }

    suspend fun getShowtimesForTheater(theaterId: Long): List<Showtime> =
        cinemaDao.getShowtimesForTheater(theaterId).map { it.toDomain() }

    fun getShowtimesForTheaterFlow(theaterId: Long): Flow<List<Showtime>> =
        cinemaDao.getShowtimesForTheaterFlow(theaterId).map { list -> list.map { it.toDomain() } }

    suspend fun bookTicket(ticket: Ticket): Long =
        cinemaDao.insertTicket(TicketEntity.fromDomain(ticket))

    suspend fun getTicketsForUser(userId: Long): List<Ticket> =
        cinemaDao.getTicketsForUser(userId).map { it.toDomain() }

    fun getTicketsForUserFlow(userId: Long): Flow<List<Ticket>> =
        cinemaDao.getTicketsForUserFlow(userId).map { list -> list.map { it.toDomain() } }
}
