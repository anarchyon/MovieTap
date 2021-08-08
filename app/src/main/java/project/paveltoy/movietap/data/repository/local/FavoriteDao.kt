package project.paveltoy.movietap.data.repository.local

import androidx.room.*
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.repository.local.entities.FavoriteMovies

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM MovieEntity")
    fun getFavoriteMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(movie: MovieEntity)

    @Delete
    fun deleteFromFavorite(movie: MovieEntity)
}