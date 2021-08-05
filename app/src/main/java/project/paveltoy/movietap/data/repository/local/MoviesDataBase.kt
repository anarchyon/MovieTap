package project.paveltoy.movietap.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMovies::class], version = 1, exportSchema = false)
abstract class MoviesDataBase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}