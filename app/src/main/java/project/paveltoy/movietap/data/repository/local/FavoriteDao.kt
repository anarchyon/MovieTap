package project.paveltoy.movietap.data.repository.local

import androidx.room.*
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.repository.local.entities.FavoriteMovies

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteMovies")
    fun getFavoriteMovies(): List<FavoriteMovies>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favoriteMovies: FavoriteMovies)

    @Delete
    fun deleteFromFavorite(favoriteMovies: FavoriteMovies)
}