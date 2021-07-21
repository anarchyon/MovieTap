package project.paveltoy.movietap.viewmodels

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import project.paveltoy.movietap.data.*

class MainViewModel : ViewModel() {
    //    private val movieRepo: MovieRepo = FakeMovieRepo()
    private val movieRepo: MovieRepo = TMDBMovieRepo()
    private var genresList = hashMapOf<Int, String>()
    private lateinit var movieGenres: MovieGenres
    private val movieStandardSubunits = MovieStandardSubunits(Resources.getSystem())

    val clickedMovieLiveData = MutableLiveData<MovieEntity>()
    val movieSubunitToDisplayList = MutableLiveData<List<String>>()

    init {
        val list = arrayListOf<String>()
        movieStandardSubunits.subunits.forEach {
            list.add(it.subunit)
        }
        movieSubunitToDisplayList.value = list
    }

    fun getMovies(): Map<Int, ArrayList<MovieEntity>> {
        return getSectionMovies(movieRepo.getMovies())
    }

    fun getFavoriteMovies(): List<MovieEntity> {
        return getFavoriteMovies(movieRepo.getMovies())
    }

    fun getGenres() {
        movieRepo.getGenres(this::genresParse)
    }

    private fun genresParse(genresJSON: String) {
        movieGenres = Gson().fromJson(genresJSON, MovieGenres::class.java)
    }
}