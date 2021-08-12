package project.paveltoy.movietap.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.paveltoy.app.App
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.MovieGenres
import project.paveltoy.movietap.data.entity.Movies
import project.paveltoy.movietap.data.entity.TMDBSections
import project.paveltoy.movietap.data.loader.LoadMovieResponse
import project.paveltoy.movietap.data.loader.TMDBMovieLoader
import project.paveltoy.movietap.data.repository.local.SQLiteRepo
import project.paveltoy.movietap.ui.customizes.SectionsForDisplay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TMDBMovieRepo(
    private val liveDataSectionMovieList: MutableMap<String, MutableLiveData<List<MovieEntity>>>,
    private val moviesLiveData: MutableLiveData<MutableMap<String, List<MovieEntity>>>,
) :
    MovieRepo {
    private val movieLoader = TMDBMovieLoader()
    private var movies: Movies = Movies()
    private val localRepo: SQLiteRepo = SQLiteRepo(App.getFavoriteDao())

    override fun getFavoriteMovies(favoriteLiveData: MutableLiveData<List<MovieEntity>>) {
        CoroutineScope(Dispatchers.Default).launch {
            favoriteLiveData.postValue(localRepo.getFavoriteMovies())
        }
    }

    override fun addToFavorite(movie: MovieEntity) {
        CoroutineScope(Dispatchers.Default).launch {
            localRepo.addToFavorite(movie)
        }
    }

    override fun removeFromFavorite(
        movie: MovieEntity,
        favoriteLiveData: MutableLiveData<List<MovieEntity>>
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            localRepo.removeFromFavorite(movie)
            favoriteLiveData.postValue(localRepo.getFavoriteMovies())
        }
    }

    override fun getMovieSections(): List<String> {
        return movies.sectionsForDisplay.sections.keys.toList()
    }

    fun getSectionForDisplay(): SectionsForDisplay {
        return movies.sectionsForDisplay
    }

    fun getLoadedMovies(): Map<String, List<MovieEntity>> = movies.movieSet

    override fun getMovies(): Map<String, List<MovieEntity>> {
        CoroutineScope(Dispatchers.Main).launch {
            if (movies.movieGenres.genres.isEmpty()) getGenres()
            getMoviesFromRepo()
        }
        return movies.movieSet
    }

    private fun getMoviesFromRepo() {
        movies.sectionsForDisplay.sections.let { map ->
            map.keys.forEach { section ->
                if (map[section] == true) {
                    val request = TMDBSections.SECTIONS.find { it.section == section }?.request
                    val genreId = movies.movieGenres.genres.find { it.name == section }?.id
                    if (request != null) {
                        loadMoviesBySection(section, request)
                    }
                    if (genreId != null) {
                        loadMoviesByGenre(section, genreId)
                    }
                }
            }
        }
    }

    private fun loadMoviesBySection(section: String, request: String) {
        movieLoader.loadMovieBySection(
            section,
            request,
            callback = object : Callback<LoadMovieResponse> {
                override fun onResponse(
                    call: Call<LoadMovieResponse>,
                    response: Response<LoadMovieResponse>
                ) {
                    val result: LoadMovieResponse? = response.body()
                    if (response.isSuccessful && result != null) {
                        movies.movieSet[section] = result.results
                        movies.movieSet[section]?.forEach { movie ->
                            movie.poster_path?.let {
                                movie.poster_path = movieLoader.completePosterPath(it)
                            }
                        }
                        liveDataSectionMovieList[section]?.value = movies.movieSet[section]
                    }
                }

                override fun onFailure(call: Call<LoadMovieResponse>, t: Throwable) {
                    Log.d("@@@", t.message!!)
                }

            })
    }

    private fun loadMoviesByGenre(section: String, genreId: Int) {
        movieLoader.loadMovieByGenre(
            section,
            genreId,
            callback = object : Callback<LoadMovieResponse> {
                override fun onResponse(
                    call: Call<LoadMovieResponse>,
                    response: Response<LoadMovieResponse>
                ) {
                    val result: LoadMovieResponse? = response.body()
                    if (response.isSuccessful && result != null) {
                        movies.movieSet[section] = result.results
                        movies.movieSet[section]?.forEach { movie ->
                            movie.poster_path?.let {
                                movie.poster_path = movieLoader.completePosterPath(it)
                            }
                        }
                        liveDataSectionMovieList[section]?.value = movies.movieSet[section]
                    }
                }

                override fun onFailure(call: Call<LoadMovieResponse>, t: Throwable) {
                }

            })

    }

    override fun getGenres() {
        movieLoader.loadGenres(callback = object : Callback<MovieGenres> {
            override fun onResponse(call: Call<MovieGenres>, response: Response<MovieGenres>) {
                val result: MovieGenres? = response.body()
                if (response.isSuccessful && result != null) {
                    movies.movieGenres = result
                    insertGenresToSections()
                }
            }

            override fun onFailure(call: Call<MovieGenres>, t: Throwable) {
            }
        })
    }

    override fun setMovieSectionsList(sectionsForDisplay: SectionsForDisplay?) {
        if (sectionsForDisplay == null) {
            TMDBSections.SECTIONS.forEach {
                movies.sectionsForDisplay.sections[it.section] = true
                fillEmptySection(it.section)
            }
            insertGenresToSections()
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                if (movies.movieGenres.genres.isEmpty()) getGenres()
                movies.sectionsForDisplay = sectionsForDisplay
            }
        }
    }

    private fun insertGenresToSections() {
        movies.movieGenres.genres.forEach {
            if (!movies.sectionsForDisplay.sections.containsKey(it.name)) {
                movies.sectionsForDisplay.sections[it.name] = false
                fillEmptySection(it.name)
            }
        }
    }

    private fun fillEmptySection(key: String) {
        if (!movies.movieSet.containsKey(key)) {
            movies.movieSet[key] = listOf()
            moviesLiveData.postValue(movies.movieSet)
        }
    }
}