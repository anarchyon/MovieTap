package project.paveltoy.movietap.data.repository.local

import project.paveltoy.movietap.data.entity.Genre
import project.paveltoy.movietap.data.entity.MovieEntity

interface LocalRepo {
    suspend fun addToFavorite(movie: MovieEntity)

    suspend fun removeFromFavorite(movie: MovieEntity)

    suspend fun getFavoriteMovies()

    suspend fun getGenres()

    suspend fun addGenre(genre: Genre)
}