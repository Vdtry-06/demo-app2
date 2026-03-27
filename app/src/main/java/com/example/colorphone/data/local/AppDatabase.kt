package com.example.colorphone.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.colorphone.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

@Database(
    entities = [
        UserEntity::class,
        MovieEntity::class,
        TheaterEntity::class,
        ShowtimeEntity::class,
        TicketEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cinemaDao(): CinemaDao

    class Callback(
        private val database: Provider<AppDatabase>,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().cinemaDao()

            scope.launch(Dispatchers.IO) {
                insertFakeData(dao)
            }
        }

        private suspend fun insertFakeData(dao: CinemaDao) {
            val user = UserEntity(1, "nguyenvana", "a@example.com", "123456")
            dao.insertUser(user)

            val movies = listOf(
                MovieEntity(1, "Mai", "Phim tâm lý tình cảm", "https://example.com/mai.jpg", 131),
                MovieEntity(2, "Đào, Phở và Piano", "Phim lịch sử", "https://example.com/dao.jpg", 120)
            )
            dao.insertMovies(movies)

            val theaters = listOf(
                TheaterEntity(1, "CGV Sư Vạn Hạnh", "Quận 10, TP.HCM"),
                TheaterEntity(2, "Galaxy Nguyễn Du", "Quận 1, TP.HCM")
            )
            dao.insertTheaters(theaters)

            val showtimes = listOf(
                ShowtimeEntity(1, 1, 1, System.currentTimeMillis() + 86400000, System.currentTimeMillis() + 94260000, "Room 1"),
                ShowtimeEntity(2, 2, 2, System.currentTimeMillis() + 172800000, System.currentTimeMillis() + 180660000, "Room 2")
            )
            dao.insertShowtimes(showtimes)
        }
    }
}
