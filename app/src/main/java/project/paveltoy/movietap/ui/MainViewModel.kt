package project.paveltoy.movietap.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.MovieGenres
import project.paveltoy.movietap.data.repository.MovieRepo
import project.paveltoy.movietap.data.repository.TMDBMovieRepo
import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

class MainViewModel : ViewModel() {
    val clickedMovieLiveData = MutableLiveData<MovieEntity>()
    var liveDataSectionMovieList = mutableMapOf<String, MutableLiveData<List<MovieEntity>>>()
    val movieSectionsLiveData = MutableLiveData<Set<String>>()
    val sectionsForDisplayLiveData = MutableLiveData<SectionsForDisplay>()
    val movieGenres = MutableLiveData<MovieGenres>()
    private val movieRepo: MovieRepo =
        TMDBMovieRepo(liveDataSectionMovieList, movieGenres)
    val favoriteMovies = MutableLiveData<List<MovieEntity>>()
    var callbackToSavePrefs: ((SectionsForDisplay) -> Unit)? = null

    fun getSectionsForDisplay(): SectionsForDisplay {
        return movieRepo.getMovieSectionsList()
    }

    fun getMovies() {
        movieRepo.getMovies()
    }

    fun getFavoriteMovies() {
        movieRepo.getFavoriteMovies(favoriteMovies)
    }

    fun addToFavorite(movie: MovieEntity) {
        movieRepo.addToFavorite(movie)
    }

    fun removeFromFavorite(movie: MovieEntity) {
        movieRepo.removeFromFavorite(movie, favoriteMovies)
    }

    fun setSectionsList(sectionsForDisplay: SectionsForDisplay?) {
        movieRepo.setMovieSectionsList(sectionsForDisplay)
        setLiveDataSectionList()
        sectionsForDisplayLiveData.value = movieRepo.getMovieSectionsList()
    }

    private fun setLiveDataSectionList() {
        val sectionsToMainScreen = mutableSetOf<String>()
        movieRepo.getMovieSectionsList().sections.let { sectionsMap ->
            sectionsMap.keys.forEach {
                if (sectionsMap[it] == true) {
                    sectionsToMainScreen.add(it)
                }
                val liveData = MutableLiveData<List<MovieEntity>>()
                liveDataSectionMovieList[it] = liveData

            }
        }
        movieSectionsLiveData.postValue(sectionsToMainScreen)
    }

    fun getMovieSections(): Set<String> {
        return movieRepo.getMovieSectionsList().sections.keys
    }

    fun getGenres() {
        movieRepo.getGenres()
    }

    fun setMainSections() {
        (movieRepo as TMDBMovieRepo).setMainMovieSectionsList()
    }
}