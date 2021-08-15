package project.paveltoy.movietap.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import project.paveltoy.movietap.data.repository.local.entities.FavoriteMovies
import project.paveltoy.movietap.data.repository.local.entities.DbMovieGenres

@Database(entities = [FavoriteMovies::class, DbMovieGenres::class], version = 1, exportSchema = false)
abstract class MoviesDataBase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}