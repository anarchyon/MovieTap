package project.paveltoy.movietap.data.loader

import project.paveltoy.movietap.data.entity.MovieGenres
import retrofit2.Callback

interface RetrofitMovieLoader {
    fun loadMovieBySection(key: String, request: String, callback: Callback<LoadMovieResponse>)
    fun loadMovieByGenre(key: String, genreId: Int, callback: Callback<LoadMovieResponse>)
    fun loadGenres(callback: Callback<MovieGenres>)
}
