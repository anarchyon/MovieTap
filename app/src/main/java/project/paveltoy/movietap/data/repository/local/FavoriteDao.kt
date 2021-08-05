package project.paveltoy.movietap.data.repository.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface FavoriteDao {
    @Query("SELECT * FROM FavoriteMovies")
    fun getFavoriteMovies(): List<FavoriteMovies>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(movie: FavoriteMovies)

    @Delete
    fun deleteFromFavorite(movie: FavoriteMovies)
}