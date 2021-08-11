package project.paveltoy.movietap.data.repository.local

import project.paveltoy.movietap.data.entity.Genre
import project.paveltoy.movietap.data.entity.MovieEntity

interface LocalRepo {
    fun addToFavorite(movie: MovieEntity)

    fun removeFromFavorite(movie: MovieEntity)

    fun getFavoriteMovies()

    fun getGenres()

    fun addGenre(genre: Genre)
}