package project.paveltoy.movietap.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.app.App.Companion.getFavoriteDao
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.Movies
import project.paveltoy.movietap.data.repository.MovieRepo
import project.paveltoy.movietap.data.repository.TMDBMovieRepo
import project.paveltoy.movietap.data.repository.local.SQLiteRepo

class MainViewModel : ViewModel() {
    val clickedMovieLiveData = MutableLiveData<MovieEntity>()
    val movieToDisplayPreferences = MutableLiveData<List<String>>()
    val liveDataSectionList = hashMapOf<String, MutableLiveData<List<MovieEntity>>>()
    val moviesLiveData = MutableLiveData<Movies>()
    private val movieRepo: MovieRepo = TMDBMovieRepo(liveDataSectionList)
    private val localRepo: SQLiteRepo = SQLiteRepo(getFavoriteDao())

    fun getMovieSections(): List<String> {
        return movieRepo.getMovieSections()
    }

    fun getMovies(): Map<String, List<MovieEntity>> {
        return movieRepo.getMovies()
    }

    fun getFavoriteMovies(): List<MovieEntity> {
        return localRepo.getFavoriteMovies()
    }

    fun addToFavorite(movie: MovieEntity) {
        localRepo.addToFavorite(movie)
    }

    fun removeFromFavorite(movie: MovieEntity) {
        localRepo.removeFromFavorite(movie)
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