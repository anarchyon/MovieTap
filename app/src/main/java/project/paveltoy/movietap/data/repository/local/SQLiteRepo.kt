package project.paveltoy.movietap.data.repository.local

import project.paveltoy.movietap.data.entity.MovieEntity

class SQLiteRepo(private val favoriteDao: FavoriteDao) : LocalRepo {
    override fun addToFavorite(movie: MovieEntity) {
        favoriteDao.addToFavorite(movie)
    }

    override fun removeFromFavorite(movie: MovieEntity) {
        favoriteDao.deleteFromFavorite(movie)
    }

    override fun getFavoriteMovies(): List<MovieEntity> {
        return favoriteDao.getFavoriteMovies()
    }
}