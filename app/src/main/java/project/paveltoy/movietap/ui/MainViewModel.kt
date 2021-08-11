package project.paveltoy.movietap.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.app.App.Companion.getFavoriteDao
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.MovieGenres
import project.paveltoy.movietap.data.entity.Movies
import project.paveltoy.movietap.data.entity.TMDBSections
import project.paveltoy.movietap.data.repository.MovieRepo
import project.paveltoy.movietap.data.repository.TMDBMovieRepo
import project.paveltoy.movietap.data.repository.local.SQLiteRepo
import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

class MainViewModel : ViewModel() {
    val clickedMovieLiveData = MutableLiveData<MovieEntity>()
    val movieToDisplayPreferences = MutableLiveData<List<String>>()
    val liveDataSectionMovieList = hashMapOf<String, MutableLiveData<List<MovieEntity>>>()
    private val movieRepo: MovieRepo = TMDBMovieRepo(liveDataSectionMovieList)
    private val localRepo: SQLiteRepo = SQLiteRepo(getFavoriteDao(), this::readFavorites, this::readGenres)

    private fun readGenres(movieGenres: MovieGenres) {
        
    }

    val moviesLiveData = MutableLiveData<Movies>()
    val favoriteMovies = MutableLiveData<List<MovieEntity>>()
    var callbackToSavePrefs: ((SectionsForDisplay) -> Unit)? = null

    fun getSectionsForDisplay(): SectionsForDisplay {
        return (movieRepo as TMDBMovieRepo).getSectionForDisplay()
    }

    fun getMovies(): Map<String, List<MovieEntity>> {
        val allSectionsMovies = movieRepo.getMovies()
        val selectedSectionsMovies = hashMapOf<String, List<MovieEntity>>()
        (movieRepo as TMDBMovieRepo).getSectionForDisplay().sections.let { sectionsMap ->
            sectionsMap.keys.forEach {
                if (sectionsMap[it] == true && allSectionsMovies.containsKey(it)) {
                    selectedSectionsMovies[it] = allSectionsMovies[it]!!
                }
            }
        }
        return selectedSectionsMovies
    }

    fun getFavoriteMovies() {
        return localRepo.getFavoriteMovies()
    }

    private fun readFavorites(favorites: List<MovieEntity>) {
        favoriteMovies.value = favorites
    }

    fun addToFavorite(movie: MovieEntity) {
        localRepo.addToFavorite(movie)
    }

    fun removeFromFavorite(movie: MovieEntity) {
        localRepo.removeFromFavorite(movie)
    }

    fun setSectionsList(sectionsForDisplay: SectionsForDisplay?) {
        movieRepo.getGenres()
        movieRepo.setMovieSectionsList(sectionsForDisplay)
//        movieRepo.setMovieSectionsList(null)
        setLiveDataSectionList()
    }

    private fun setLiveDataSectionList() {
        movieRepo.getMovieSections().forEach {
            val liveData = MutableLiveData<List<MovieEntity>>()
            liveDataSectionMovieList[it] = liveData
        }
    }

    fun getTMDBSectionsSize(): Int {
        return TMDBSections.SECTIONS.size
    }
}