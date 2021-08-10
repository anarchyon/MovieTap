package project.paveltoy.movietap.data.repository

import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

interface MovieRepo {
    fun getMovies(): Map<String, List<MovieEntity>>
    fun getGenres()
    fun setMovieSectionsList(sectionsForDisplay: SectionsForDisplay?)
    fun getMovieSections(): List<String>
}