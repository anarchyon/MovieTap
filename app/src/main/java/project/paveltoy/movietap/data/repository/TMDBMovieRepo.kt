package project.paveltoy.movietap.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

class TMDBMovieRepo(private val liveDataSectionMovieList: HashMap<String, MutableLiveData<List<MovieEntity>>>) :
    MovieRepo {
    private val movieLoader = TMDBMovieLoader()
    private var movies: Movies = Movies()
    private val localRepo: SQLiteRepo = SQLiteRepo(App.getFavoriteDao(), this::readFavorites, this::readGenres)

    suspend fun getFavoriteMovies() {
        return localRepo.getFavoriteMovies()
    }

    private fun readGenres(movieGenres: MovieGenres) {

    }

    private fun readFavorites(favorites: List<MovieEntity>) {
        favoriteMovies.value = favorites
    }

    suspend fun addToFavorite(movie: MovieEntity) {
        localRepo.addToFavorite(movie)
    }

    suspend fun removeFromFavorite(movie: MovieEntity) {
        localRepo.removeFromFavorite(movie)
    }

    override fun getMovieSections(): List<String> {
        return movies.sectionsForDisplay.sections.keys.toList()
    }

    fun getSectionForDisplay(): SectionsForDisplay {
        return movies.sectionsForDisplay
    }

    override fun getMovies(): Map<String, List<MovieEntity>> {
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
        return movies.movieSet
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
            movies.sectionsForDisplay = sectionsForDisplay
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
        }
    }
}