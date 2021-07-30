package project.paveltoy.movietap.data.repository

import project.paveltoy.movietap.data.entity.MovieEntity

interface MovieRepo {
    fun addMovie(movie: MovieEntity)
    fun getMovies(): Map<String, List<MovieEntity>>
    fun updateMovie(movie: MovieEntity)
    fun deleteMovie(movie: MovieEntity)
    fun getGenres()
    fun getSections()
    fun setMovieSectionsList(prefs: String?)
}