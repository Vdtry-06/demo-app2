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
                MovieEntity(1, "Mai", "Phim tâm lý tình cảm của Trấn Thành", "https://image.tmdb.org/t/p/w500/mai_poster.jpg", 131),
                MovieEntity(2, "Đào, Phở và Piano", "Phim lịch sử về Hà Nội 1946", "https://image.tmdb.org/t/p/w500/dao_poster.jpg", 120),
                MovieEntity(3, "Lật Mặt 7: Một Điều Ước", "Hành trình tìm lại tình cảm gia đình", "https://image.tmdb.org/t/p/w500/latmat7_poster.jpg", 138),
                MovieEntity(4, "Dune: Part Two", "Hành trình tiếp theo của Paul Atreides", "https://image.tmdb.org/t/p/w500/dune2_poster.jpg", 166),
                MovieEntity(5, "Godzilla x Kong", "Sự trỗi dậy của đế chế mới", "https://image.tmdb.org/t/p/w500/gxk_poster.jpg", 115),
                MovieEntity(6, "Kung Fu Panda 4", "Po đối đầu với Tắc Kè Bông", "https://image.tmdb.org/t/p/w500/panda4_poster.jpg", 94),
                MovieEntity(7, "Exhuma", "Quật mộ trùng tang - Kinh dị tâm linh", "https://image.tmdb.org/t/p/w500/exhuma_poster.jpg", 134),
                MovieEntity(8, "Spider-Man: Across the Spider-Verse", "Hành trình xuyên đa vũ trụ", "https://image.tmdb.org/t/p/w500/spiderman_poster.jpg", 140),
                MovieEntity(9, "Oppenheimer", "Câu chuyện về cha đẻ bom nguyên tử", "https://image.tmdb.org/t/p/w500/oppenheimer_poster.jpg", 180),
                MovieEntity(10, "Past Lives", "Muôn kiếp nhân duyên", "https://image.tmdb.org/t/p/w500/pastlives_poster.jpg", 106)
            )
            dao.insertMovies(movies)

            val theaters = listOf(
                TheaterEntity(1, "CGV Sư Vạn Hạnh", "Quận 10, TP.HCM"),
                TheaterEntity(2, "Galaxy Nguyễn Du", "Quận 1, TP.HCM"),
                TheaterEntity(3, "Lotte Cinema Nam Sài Gòn", "Quận 7, TP.HCM"),
                TheaterEntity(4, "BHD Star Thảo Điền", "Quận 2, TP.HCM"),
                TheaterEntity(5, "CGV Vincom Center Đồng Khởi", "Quận 1, TP.HCM"),
                TheaterEntity(6, "Galaxy Kinh Dương Vương", "Quận 6, TP.HCM")
            )
            dao.insertTheaters(theaters)

            val showtimes = mutableListOf<ShowtimeEntity>()
            var showtimeId = 1L
            val now = System.currentTimeMillis()
            val hourMs = 3600000L
            val dayMs = 86400000L

            for (mId in 1L..10L) {
                for (tId in 1L..3L) { // Mỗi phim chiếu ở 3 rạp đầu
                    showtimes.add(ShowtimeEntity(
                        showtimeId++, 
                        mId, 
                        tId, 
                        now + (mId * 2 + tId) * hourMs, 
                        now + (mId * 2 + tId + 2) * hourMs, 
                        "Room ${tId + 1}"
                    ))
                    // Thêm suất chiếu cho ngày mai
                    showtimes.add(ShowtimeEntity(
                        showtimeId++, 
                        mId, 
                        tId, 
                        now + dayMs + (mId + tId) * hourMs, 
                        now + dayMs + (mId + tId + 2) * hourMs, 
                        "Room ${tId}"
                    ))
                }
            }
            dao.insertShowtimes(showtimes)
        }
    }
}
