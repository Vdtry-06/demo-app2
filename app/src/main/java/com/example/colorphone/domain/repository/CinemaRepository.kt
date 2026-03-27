package com.example.colorphone.domain.repository

import com.example.colorphone.data.local.FakeCinemaDao
import com.example.colorphone.domain.model.Movie
import com.example.colorphone.domain.model.Showtime
import com.example.colorphone.domain.model.Theater
import com.example.colorphone.domain.model.Ticket
import com.example.colorphone.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaRepository @Inject constructor(
    private val fakeDao: FakeCinemaDao
) {
    fun getUser(userId: Long): User? = fakeDao.getUser(userId)
    fun login(email: String, passwordHash: String): User? = fakeDao.login(email, passwordHash)
    fun register(user: User): Long = fakeDao.insertUser(user)

    fun getAllMovies(): List<Movie> = fakeDao.getAllMovies()
    fun getMovieById(movieId: Long): Movie? = fakeDao.getMovieById(movieId)

    fun getAllTheaters(): List<Theater> = fakeDao.getAllTheaters()
    fun getTheaterById(theaterId: Long): Theater? = fakeDao.getTheaterById(theaterId)

    fun getShowtimesForMovie(movieId: Long): List<Showtime> = fakeDao.getShowtimesForMovie(movieId)
    fun getShowtimesForTheater(theaterId: Long): List<Showtime> = fakeDao.getShowtimesForTheater(theaterId)

    fun bookTicket(ticket: Ticket): Long = fakeDao.insertTicket(ticket)
    fun getTicketsForUser(userId: Long): List<Ticket> = fakeDao.getTicketsForUser(userId)
}
