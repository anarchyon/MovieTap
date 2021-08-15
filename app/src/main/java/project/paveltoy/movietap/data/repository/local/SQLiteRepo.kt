package project.paveltoy.movietap.data.repository.local

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.coroutineScope
import project.paveltoy.movietap.data.entity.Genre
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.MovieGenres
import project.paveltoy.movietap.data.repository.local.entities.DbMovieGenres
import project.paveltoy.movietap.data.repository.local.entities.FavoriteMovies

class SQLiteRepo(private val favoriteDao: FavoriteDao) : LocalRepo {

    override suspend fun addToFavorite(movie: MovieEntity) = coroutineScope {
        val favoriteMovie = convertMovieToDb(movie)
        favoriteDao.addToFavorite(favoriteMovie)
    }

    override suspend fun removeFromFavorite(movie: MovieEntity) = coroutineScope {
        val favoriteMovie = convertMovieToDb(movie)
        favoriteDao.deleteFromFavorite(favoriteMovie)
    }

    override suspend fun getFavoriteMovies(): List<MovieEntity> = coroutineScope {
        return@coroutineScope convertToListMovieEntity(favoriteDao.getFavoriteMovies())
    }

    override suspend fun getGenres(): MovieGenres = coroutineScope {
        val movieGenres = MovieGenres()
        movieGenres.genres = favoriteDao.getGenres()
        return@coroutineScope movieGenres
    }

    override suspend fun addGenre(genre: Genre) = coroutineScope {
        val dbMovieGenres = convertGenreToDbMovieGenre(genre)
        favoriteDao.addGenre(dbMovieGenres)
    }

    private fun convertGenreToDbMovieGenre(genre: Genre): DbMovieGenres {
        return DbMovieGenres(
            genre.id,
            genre.name
        )
    }

    private fun convertMovieToDb(movie: MovieEntity): FavoriteMovies {
        val genresString = movie.genre_ids.toString()
        return FavoriteMovies(
            movie.id,
            movie.title,
            movie.vote_average,
            movie.vote_count,
            movie.poster_path,
            movie.isFavorite,
            movie.release_date,
            movie.overview,
            genresString.substring(1, genresString.length - 1),
            movie.movieState
        )
    }

    private fun convertToListMovieEntity(favoriteMovies: List<FavoriteMovies>): List<MovieEntity> {
        val resultMovies = arrayListOf<MovieEntity>()
        favoriteMovies.forEach {
            resultMovies.add(convertToMovieEntity(it))
        }
        return resultMovies
    }

    private fun convertToMovieEntity(favoriteMovie: FavoriteMovies): MovieEntity {
        val genresList = favoriteMovie.movieGenres.split(",")
        val genresListInt = arrayListOf<Int>()
        genresList.forEach {
            genresListInt.add(it.trim().toInt())
        }
        return MovieEntity(
            favoriteMovie.id,
            favoriteMovie.title,
            favoriteMovie.vote_average,
            favoriteMovie.vote_count,
            favoriteMovie.poster_path,
            favoriteMovie.isFavorite,
            favoriteMovie.release_date,
            favoriteMovie.overview,
            genresListInt,
            favoriteMovie.movieState
        )
    }
}