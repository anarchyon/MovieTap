package project.paveltoy.movietap.data.repository

import project.paveltoy.movietap.data.entity.MovieEntity

interface MovieRepo {
    fun getMovies(): Map<String, List<MovieEntity>>
    fun getGenres()
    fun getSections()
    fun setMovieSectionsList(prefs: String?)
    fun getMovieSections(): List<String>
}