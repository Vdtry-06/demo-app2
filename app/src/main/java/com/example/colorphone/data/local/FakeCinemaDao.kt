package com.example.colorphone.data.local

import com.example.colorphone.domain.model.Movie
import com.example.colorphone.domain.model.Showtime
import com.example.colorphone.domain.model.Theater
import com.example.colorphone.domain.model.Ticket
import com.example.colorphone.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeCinemaDao @Inject constructor() {
    private val users = mutableListOf<User>()
    private val movies = mutableListOf<Movie>()
    private val theaters = mutableListOf<Theater>()
    private val showtimes = mutableListOf<Showtime>()
    private val tickets = mutableListOf<Ticket>()

    init {
        // Dữ liệu giả (Fake Data)
        users.add(User(1, "nguyenvana", "a@example.com", "123456"))
        
        movies.add(Movie(1, "Mai", "Phim tâm lý tình cảm", "https://example.com/mai.jpg", 131))
        movies.add(Movie(2, "Đào, Phở và Piano", "Phim lịch sử", "https://example.com/dao.jpg", 120))
        
        theaters.add(Theater(1, "CGV Sư Vạn Hạnh", "Quận 10, TP.HCM"))
        theaters.add(Theater(2, "Galaxy Nguyễn Du", "Quận 1, TP.HCM"))
        
        showtimes.add(Showtime(1, 1, 1, System.currentTimeMillis() + 86400000, System.currentTimeMillis() + 94260000, "Room 1"))
        showtimes.add(Showtime(2, 2, 2, System.currentTimeMillis() + 172800000, System.currentTimeMillis() + 180660000, "Room 2"))
    }

    fun getUser(userId: Long): User? = users.find { it.id == userId }
    fun login(email: String, passwordHash: String): User? = users.find { it.email == email && it.passwordHash == passwordHash }
    fun insertUser(user: User): Long {
        val newId = (users.maxOfOrNull { it.id } ?: 0L) + 1L
        users.add(user.copy(id = newId))
        return newId
    }

    fun getAllMovies(): List<Movie> = movies
    fun getMovieById(movieId: Long): Movie? = movies.find { it.id == movieId }

    fun getAllTheaters(): List<Theater> = theaters
    fun getTheaterById(theaterId: Long): Theater? = theaters.find { it.id == theaterId }

    fun getShowtimesForMovie(movieId: Long): List<Showtime> = showtimes.filter { it.movieId == movieId }
    fun getShowtimesForTheater(theaterId: Long): List<Showtime> = showtimes.filter { it.theaterId == theaterId }

    fun insertTicket(ticket: Ticket): Long {
        val newId = (tickets.maxOfOrNull { it.id } ?: 0L) + 1L
        tickets.add(ticket.copy(id = newId))
        return newId
    }
    fun getTicketsForUser(userId: Long): List<Ticket> = tickets.filter { it.userId == userId }
}
