package project.paveltoy.movietap.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.MovieGenres
import project.paveltoy.movietap.data.entity.Movies
import project.paveltoy.movietap.data.entity.TMDBSections
import project.paveltoy.movietap.data.loader.LoadMovieResponse
import project.paveltoy.movietap.data.loader.TMDBMovieLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TMDBMovieRepo(private val liveDataSectionMovieList: HashMap<String, MutableLiveData<List<MovieEntity>>>) :
    MovieRepo {
    private val movieLoader = TMDBMovieLoader()
    private var movies: Movies = Movies()

    override fun getMovieSections(): List<String> {
        return movies.movieSectionsList.toList().plus(movies.movieGenresList)
    }

    override fun getMovies(): Map<String, List<MovieEntity>> {
        movies.movieSectionsList.forEach { s ->
            if (movies.movieSet.containsKey(s)) {
                val request = TMDBSections.SECTIONS.find { it.section == s }?.request
                if (request != null) {
                    movieLoader.loadMovieBySection(
                        s,
                        request,
                        callback = object : Callback<LoadMovieResponse> {
                            override fun onResponse(
                                call: Call<LoadMovieResponse>,
                                response: Response<LoadMovieResponse>
                            ) {
                                val result: LoadMovieResponse? = response.body()
                                if (response.isSuccessful && result != null) {
                                    movies.movieSet[s] = result.results
                                    movies.movieSet[s]?.forEach {
                                        it.poster_path = movieLoader.completePosterPath(it.poster_path)
                                    }
                                    liveDataSectionMovieList[s]?.value = movies.movieSet[s]
                                }
                            }

                            override fun onFailure(call: Call<LoadMovieResponse>, t: Throwable) {
                                Log.d("@@@", t.message!!)
                            }

                        })
                }
            }
        }
        movies.movieGenresList.forEach { s ->
            if (!movies.movieSet.containsKey(s)) {
                val id = movies.movieGenres?.genres?.find { it.name == s }?.id
                if (id != null) {
                    movieLoader.loadMovieByGenre(
                        s,
                        id,
                        callback = object : Callback<LoadMovieResponse> {
                            override fun onResponse(
                                call: Call<LoadMovieResponse>,
                                response: Response<LoadMovieResponse>
                            ) {
                                val result: LoadMovieResponse? = response.body()
                                if (response.isSuccessful && result != null) {
                                    movies.movieSet[s] = result.results
                                    movies.movieSet[s]?.forEach {
                                        it.poster_path = movieLoader.completePosterPath(it.poster_path)
                                    }
                                    liveDataSectionMovieList[s]?.value = movies.movieSet[s]
                                }
                            }

                            override fun onFailure(call: Call<LoadMovieResponse>, t: Throwable) {
                            }

                        })
                }
            }
        }
        return movies.movieSet
    }

    override fun getGenres() {
        movieLoader.loadGenres(callback = object : Callback<MovieGenres> {
            override fun onResponse(call: Call<MovieGenres>, response: Response<MovieGenres>) {
                val result: MovieGenres? = response.body()
                if (response.isSuccessful && result != null) {
                    movies.movieGenres = result
                }
            }

            override fun onFailure(call: Call<MovieGenres>, t: Throwable) {
            }
        })
    }

    override fun setMovieSectionsList(prefJSON: String?) {
        if (prefJSON == null) {
            movies.movieSectionsList = listOf(
                TMDBSections.SECTIONS[0].section,
                TMDBSections.SECTIONS[1].section,
                TMDBSections.SECTIONS[2].section,
            )
        }
        movies.movieSectionsList.forEach {
            fillEmptySection(it)
        }
        movies.movieGenresList.forEach {
            fillEmptySection(it)
        }
    }

    private fun fillEmptySection(key: String) {
        if (!movies.movieSet.containsKey(key)) {
            movies.movieSet[key] = listOf()
        }
    }
}