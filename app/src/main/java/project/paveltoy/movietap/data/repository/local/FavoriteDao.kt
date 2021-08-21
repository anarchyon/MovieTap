package project.paveltoy.movietap.data.repository.local

import android.database.Cursor
import androidx.room.*
import project.paveltoy.movietap.data.entity.Genre
import project.paveltoy.movietap.data.repository.local.entities.DbMovieGenres
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
    fun getGenres(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addGenre(dbMovieGenres: DbMovieGenres)

    @Query("DELETE FROM FavoriteMovies WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM FavoriteMovies")
    fun getFavoriteMoviesCursor(): Cursor
}