package project.paveltoy.movietap.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.TMDBSections
import project.paveltoy.movietap.data.repository.MovieRepo
import project.paveltoy.movietap.data.repository.TMDBMovieRepo
import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

class MainViewModel : ViewModel() {
    val clickedMovieLiveData = MutableLiveData<MovieEntity>()

    //    val movieToDisplayPreferences = MutableLiveData<List<String>>()
    val liveDataSectionMovieList = mutableMapOf<String, MutableLiveData<List<MovieEntity>>>()
    val movieSectionsLiveData = MutableLiveData<Set<String>>()

    //    val moviesToDisplayLiveData = MutableLiveData<MutableMap<String, List<MovieEntity>>>()
    private val movieRepo: MovieRepo = TMDBMovieRepo(liveDataSectionMovieList)
    val favoriteMovies = MutableLiveData<List<MovieEntity>>()
    var callbackToSavePrefs: ((SectionsForDisplay) -> Unit)? = null

    fun getSectionsForDisplay(): SectionsForDisplay {
        return movieRepo.getMovieSectionsList()
    }

    fun getMovies()/*: Map<String, List<MovieEntity>>*/ {
        val sectionsForDisplay = movieRepo.getMovieSectionsList()
        /*val allSectionsMovies = */if (isSelectedMoviesLoaded(sectionsForDisplay)) {
            (movieRepo as TMDBMovieRepo).getLoadedMovies()
        } else {
            //пока грузятся все фильмы, если не загружен хотя бы один раздел, потом изменю
            movieRepo.getMovies()
        }
//        val selectedSectionsMovies = mutableMapOf<String, List<MovieEntity>>()
//        sectionsForDisplay.sections.let { sectionsMap ->
//            sectionsMap.keys.forEach {
//                if (sectionsMap[it] == true && allSectionsMovies.containsKey(it)) {
//                    selectedSectionsMovies[it] = allSectionsMovies[it]!!
//                }
//            }
//        }
//        moviesToDisplayLiveData.value = selectedSectionsMovies
//        return selectedSectionsMovies
    }

    private fun isSelectedMoviesLoaded(sectionsForDisplay: SectionsForDisplay): Boolean {
        val loadedMovies = (movieRepo as TMDBMovieRepo).getLoadedMovies()
        if (loadedMovies.isEmpty()) return false
        sectionsForDisplay.sections.let { sectionMap ->
            sectionMap.keys.forEach {
                if (!loadedMovies.containsKey(it)) return false
            }
        }
        return true
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
        CoroutineScope(Dispatchers.Default).launch {
            movieRepo.setMovieSectionsList(sectionsForDisplay)
            setLiveDataSectionList()
            getMovies()
        }
    }

    private fun setLiveDataSectionList() {
        val sectionsToMainScreen = mutableSetOf<String>()
        movieRepo.getMovieSectionsList().sections.let { sectionsMap ->
            sectionsMap.keys.forEach {
                if (sectionsMap[it] == true) sectionsToMainScreen.add(it)
                val liveData = MutableLiveData<List<MovieEntity>>()
                liveDataSectionMovieList[it] = liveData
            }
        }
        movieSectionsLiveData.postValue(sectionsToMainScreen)
    }

    fun getTMDBSectionsSize(): Int {
        return TMDBSections.SECTIONS.size
    }

    fun getMovieSections(): Set<String> {
        return movieRepo.getMovieSectionsList().sections.keys
    }
}