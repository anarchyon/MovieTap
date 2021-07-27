package project.paveltoy.movietap.data

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson

class TMDBMovieRepo(private val moviesLiveData: MutableLiveData<Movies>) : MovieRepo {
    private val movieLoader = TMDBMovieLoader()
    private var movies: Movies = Movies()
    private lateinit var movieGenres: MovieGenres

    override fun addMovie(movie: MovieEntity) {

    }

    override fun getMovies(): Map<String, List<MovieEntity>> {
        movies.movieSectionsList?.forEach { s ->
            if (!movies.movies.containsKey(s)) {
                val request = TMDBSections.SECTIONS.find { it.section == s }?.request
                if (request != null) {
                    movieLoader.loadMovieBySection(s, request, this::movieParse)
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

    override fun updateMovie(movie: MovieEntity) {

    }

    override fun deleteMovie(movie: MovieEntity) {

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
    }
}