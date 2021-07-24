package project.paveltoy.movietap.viewmodels

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.movietap.data.*

class MainViewModel : ViewModel() {
    //    private val movieRepo: MovieRepo = FakeMovieRepo()
    private val movieRepo: MovieRepo = TMDBMovieRepo()

    val clickedMovieLiveData = MutableLiveData<MovieEntity>()
    val movieToDisplayPreferences = MutableLiveData<List<String>>()

    fun getMovies(): Map<String, List<MovieEntity>> {
        return movieRepo.getMovies()
    }

    fun getFavoriteMovies(): List<MovieEntity> {
//        return getFavoriteMovies(movieRepo.getMovies())
        return listOf()
    }

    fun setDefaultSectionsList(resources: Resources) {
        movieRepo.setMovieSectionsList()
        listOf(
            TMDBSections.SECTIONS[0].section,
            TMDBSections.SECTIONS[1].section,
            TMDBSections.SECTIONS[2].section,
        )
    }
}