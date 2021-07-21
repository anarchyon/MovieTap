package project.paveltoy.movietap.data

class TMDBMovieRepo : MovieRepo {
    private val movieLoader = TMDBMovieLoader()

    override fun addMovie(movie: MovieEntity) {

    }

    override fun getMovies(): List<MovieEntity> {
        return listOf()
    }

    override fun updateMovie(movie: MovieEntity) {

    }

    override fun deleteMovie(movie: MovieEntity) {

    }

    override fun getGenres(loadListener: (String) -> Unit) {
        movieLoader.loadGenres(loadListener)
    }

    override fun getSubunits(loadListener: (String) -> Unit) {
    }
}