package project.paveltoy.movietap.data.repository.local

import androidx.room.*
import project.paveltoy.movietap.data.entity.Genre
import project.paveltoy.movietap.data.entity.MovieGenres
import project.paveltoy.movietap.data.repository.local.entities.FavoriteMovies

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteMovies")
    fun getFavoriteMovies(): List<FavoriteMovies>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favoriteMovies: FavoriteMovies)

    @Delete
    fun deleteFromFavorite(favoriteMovies: FavoriteMovies)

    @Query("SELECT * FROM DbMovieGenres")
    fun getGenres(): MovieGenres

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addGenre(genre: Genre)
}