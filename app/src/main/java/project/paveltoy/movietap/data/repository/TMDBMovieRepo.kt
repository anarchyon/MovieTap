package project.paveltoy.movietap.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.entity.MovieGenres
import project.paveltoy.movietap.data.entity.Movies
import project.paveltoy.movietap.data.entity.TMDBSections
import project.paveltoy.movietap.data.loader.LoadMovieResponse
import project.paveltoy.movietap.data.loader.TMDBMovieLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TMDBMovieRepo(private val moviesLiveData: MutableLiveData<Movies>) : MovieRepo {
    private val movieLoader = TMDBMovieLoader()
    private var movies: Movies = Movies()
    private lateinit var movieGenres: MovieGenres

    override fun getMovieSections(): List<String> {
        return movies.movieSectionsList.toList().plus(movies.movieGenresList)
    }

    override fun getMovies(): Map<String, List<MovieEntity>> {
        movies.movieSectionsList?.forEach { s ->
            if (!movies.movies.containsKey(s)) {
                val request = TMDBSections.SECTIONS.find { it.section == s }?.request
                if (request != null) {
                    movieLoader.loadMovieBySection(s, request, callback = object : Callback<LoadMovieResponse>{
                        override fun onResponse(
                            call: Call<LoadMovieResponse>,
                            response: Response<LoadMovieResponse>
                        ) {
                            val result: LoadMovieResponse? = response.body()
                            if (response.isSuccessful && result != null) {
                                movies.movies[s] = result.results
                            }
                        }

                        override fun onFailure(call: Call<LoadMovieResponse>, t: Throwable) {

                        }

                    })
                }
            }
        }
        movies.movieGenresList?.forEach { s ->
            if (!movies.movies.containsKey(s)) {
                val id = movieGenres.genres.find { it.name == s }?.id
                if (id != null) {
                    movieLoader.loadMovieByGenre(s, id, this::movieParse)
                }
            }
        }
        return movies.movies
    }

    private fun movieParse(movieJSON: String, key: String?) {
        val loadResult: LoadMovieResponse = Gson().fromJson(movieJSON, LoadMovieResponse::class.java)
        if (key!= null) {
            movies.movies[key] = loadResult.results
            moviesLiveData.postValue(movies)
        }
    }

    override fun getGenres() {
        movieLoader.loadGenres(this::genresParse)
    }

    private fun genresParse(genresJSON: String, key: String?) {
        movieGenres = Gson().fromJson(genresJSON, MovieGenres::class.java)
    }

    override fun getSections() {
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
        if (!movies.movies.containsKey(key)) {
            movies.movies[key] = listOf()
        }
    }
}