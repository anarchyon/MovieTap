package project.paveltoy.movietap.data.repository

import androidx.lifecycle.MutableLiveData
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

interface MovieRepo {
    fun getMovies()/*: Map<String, List<MovieEntity>>*/
    fun getGenres()
    fun setMovieSectionsList(sectionsForDisplay: SectionsForDisplay?)
    fun getMovieSectionsList(): SectionsForDisplay
    fun getFavoriteMovies(favoriteLiveData: MutableLiveData<List<MovieEntity>>)
    fun addToFavorite(movie: MovieEntity)
    fun removeFromFavorite(movie: MovieEntity, favoriteLiveData: MutableLiveData<List<MovieEntity>>)
}