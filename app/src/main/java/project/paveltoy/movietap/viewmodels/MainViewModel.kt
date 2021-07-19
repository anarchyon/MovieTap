package project.paveltoy.movietap.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.movietap.data.*

class MainViewModel : ViewModel() {
    val clickedMovieLiveData = MutableLiveData<MovieEntity>()
    private val movieRepo: MovieRepo = FakeMovieRepo()
    val favoriteMoviesCount = MutableLiveData<Int>()

    init {
        getFavoriteMovies()
    }


    fun getMovies(): Map<Int, ArrayList<MovieEntity>> {
        return getSectionMovies(movieRepo.getMovies())
    }

    fun getFavoriteMovies(): List<MovieEntity> {
        val favoriteMovies = getFavoriteMovies(movieRepo.getMovies())
        favoriteMoviesCount.value = favoriteMovies.size
        return favoriteMovies
    }
}