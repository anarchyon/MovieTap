package project.paveltoy.movietap.data.repository.local

import android.os.Handler
import android.os.Looper
import project.paveltoy.movietap.data.entity.MovieEntity

class SQLiteRepo(private val favoriteDao: FavoriteDao) : LocalRepo {
    override fun addToFavorite(movie: MovieEntity) {
        Thread {
            favoriteDao.addToFavorite(movie)
        }.start()
    }

    override fun removeFromFavorite(movie: MovieEntity) {
        Thread {
            favoriteDao.deleteFromFavorite(movie)
        }.start()
    }

    override fun getFavoriteMovies(callback: (List<MovieEntity>) -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val moviesResult = favoriteDao.getFavoriteMovies()
            handler.post {
                callback(moviesResult)
            }
        }.start()
    }
}