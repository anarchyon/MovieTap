package project.paveltoy.movietap.data

class FakeMovieRepo : MovieRepo {
    private var movies = arrayListOf(
        MovieEntity(
            1, "Фильм1", 8.81, 1254, null,
            false, "2015", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            2, "Фильм2", 8.81, 1254, null,
            false, "2021", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            3, "Фильм8", 8.81, 1254, null,
            false, "2021", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            4, "Фильм9", 8.81, 1254, null,
            false, "2021", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            5, "Фильм10", 8.81, 1254, null,
            false, "2021", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            6, "Фильм11", 8.81, 1254, null,
            false, "2021", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            7, "Фильм3", 5.8, 454, null,
            true, "2013", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            8, "Фильм4", 8.81, 1254, null,
            false, "2023", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            9, "Фильм5", 8.1, 954, null,
            true, "1997", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            10, "Фильм6", 6.4, 5487, null,
            false, "2004", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            11, "Фильм7", 8.81, 1254, null,
            false, "2021", "", MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        )
    )

    override fun addMovie(movie: MovieEntity) {
        movies.add(movie)
    }

    override fun getMovies(): List<MovieEntity> {
        return movies
    }

    override fun updateMovie(movie: MovieEntity) {
        movies[movies.indexOf(movie)] = movie
    }

    override fun deleteMovie(movie: MovieEntity) {
        movies.remove(movie)
    }

    override fun getGenres(loadListener: (String) -> Unit) {

    }

    override fun getSubunits(loadListener: (String) -> Unit) {

    }
}