package project.paveltoy.movietap.data.repository.local

import android.os.Handler
import android.os.Looper
import project.paveltoy.movietap.data.entity.Genre
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.MovieGenres
import project.paveltoy.movietap.data.repository.local.entities.FavoriteMovies

class SQLiteRepo(
    private val favoriteDao: FavoriteDao,
    private val callbackMovies: (List<MovieEntity>) -> Unit,
    private val callbackGenres: (MovieGenres) -> Unit
) : LocalRepo {
    override fun addToFavorite(movie: MovieEntity) {
        Thread {
            val favoriteMovie = convertMovieToDb(movie)
            favoriteDao.addToFavorite(favoriteMovie)
        }.start()
    }

    override fun removeFromFavorite(movie: MovieEntity) {
        Thread {
            val favoriteMovie = convertMovieToDb(movie)
            favoriteDao.deleteFromFavorite(favoriteMovie)
            getFavoriteMovies()
        }.start()
    }

    override fun getFavoriteMovies() {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val moviesResult = convertToListMovieEntity(favoriteDao.getFavoriteMovies())
            handler.post {
                callbackMovies(moviesResult)
            }
        }.start()
    }

    override fun getGenres() {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val genresResult = favoriteDao.getGenres()
            handler.post {
                callbackGenres(genresResult)
            }
        }.start()
    }

    override fun addGenre(genre: Genre) {
        Thread {
            favoriteDao.addGenre(genre)
        }.start()
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