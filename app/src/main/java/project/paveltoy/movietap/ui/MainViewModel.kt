package project.paveltoy.movietap.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.Movies
import project.paveltoy.movietap.data.repository.MovieRepo
import project.paveltoy.movietap.data.repository.TMDBMovieRepo

class MainViewModel : ViewModel() {
    val clickedMovieLiveData = MutableLiveData<MovieEntity>()
    val movieToDisplayPreferences = MutableLiveData<List<String>>()
    val moviesLiveData = MutableLiveData<Movies>()
    //    private val movieRepo: MovieRepo = FakeMovieRepo()
    private val movieRepo: MovieRepo = TMDBMovieRepo(moviesLiveData)
    val liveDataSectionList = hashMapOf<String, MutableLiveData<List<MovieEntity>>>()

    fun getMovieSections(): List<String> {
        return movieRepo.getMovieSections()
    }

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
        setLiveDataSectionList()
    }

    private fun setLiveDataSectionList() {
        movieRepo.getMovieSections().forEach {
            val liveData = MutableLiveData<List<MovieEntity>>()
            liveDataSectionList[it] = liveData
        }
    }
}