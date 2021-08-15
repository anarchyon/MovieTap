package project.paveltoy.movietap.data.repository.local

import project.paveltoy.movietap.data.entity.Genre
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.MovieGenres

interface LocalRepo {
    suspend fun addToFavorite(movie: MovieEntity)

    suspend fun removeFromFavorite(movie: MovieEntity)

    suspend fun getFavoriteMovies(): List<MovieEntity>

    suspend fun getGenres(): MovieGenres

    suspend fun addGenre(genre: Genre)
}