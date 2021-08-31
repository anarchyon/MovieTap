package project.paveltoy.movietap.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.movietap.data.*

class MainViewModel : ViewModel() {
    private val movieRepo: MovieRepo = FakeMovieRepo()
    val clickedMovieLiveData = MutableLiveData<MovieEntity>()

    fun getMovies(): Map<Int, ArrayList<MovieEntity>> {
        return getSectionMovies(movieRepo.getMovies())
    }

    fun getFavoriteMovies(): List<MovieEntity> {
        return getFavoriteMovies(movieRepo.getMovies())
    }
}