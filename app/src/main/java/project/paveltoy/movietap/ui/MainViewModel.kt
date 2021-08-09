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
    val liveDataSectionMovieList = hashMapOf<String, MutableLiveData<List<MovieEntity>>>()
    private val movieRepo: MovieRepo = TMDBMovieRepo(liveDataSectionMovieList)
    private val localRepo: SQLiteRepo = SQLiteRepo(getFavoriteDao())
    val moviesLiveData = MutableLiveData<Movies>()
    val favoriteMovies = MutableLiveData<List<MovieEntity>>()

    fun getMovieSections(): List<String> {
        return movieRepo.getMovieSections()
    }

    fun getMovies(): Map<String, List<MovieEntity>> {
        return movieRepo.getMovies()
    }

    fun getFavoriteMovies() {
        return localRepo.getFavoriteMovies(this::readFavorites)
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

    fun setDefaultSectionsList() {
        movieRepo.getGenres()
        movieRepo.setMovieSectionsList(null)
        setLiveDataSectionList()
    }

    private fun setLiveDataSectionList() {
        movieRepo.getMovieSections().forEach {
            val liveData = MutableLiveData<List<MovieEntity>>()
            liveDataSectionMovieList[it] = liveData
        }
    }
}