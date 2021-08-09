package project.paveltoy.movietap.data.repository.local

import project.paveltoy.movietap.data.entity.MovieEntity

interface LocalRepo {
    fun addToFavorite(movie: MovieEntity)

    fun removeFromFavorite(movie: MovieEntity)

    fun getFavoriteMovies(callback: (List<MovieEntity>) -> Unit)
}