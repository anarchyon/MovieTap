package project.paveltoy.movietap.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.movietap.data.*

class MainViewModel : ViewModel() {
    val clickedMovieLiveData = MutableLiveData<MovieEntity>()
    val movieToDisplayPreferences = MutableLiveData<List<String>>()
    val moviesLiveData = MutableLiveData<Movies>()
    //    private val movieRepo: MovieRepo = FakeMovieRepo()
    private val movieRepo: MovieRepo = TMDBMovieRepo(moviesLiveData)

    fun getMovies(): Map<String, List<MovieEntity>> {
        return movieRepo.getMovies()
    }

    fun getFavoriteMovies(): List<MovieEntity> {
//        return getFavoriteMovies(movieRepo.getMovies())
        return listOf()
    }

    fun setDefaultSectionsList() {
        movieRepo.getGenres()
        movieRepo.setMovieSectionsList(null)
    }
}