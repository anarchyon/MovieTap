package project.paveltoy.movietap.data

class FakeMovieRepo : MovieRepo {
    private var movies = arrayListOf(
        MovieEntity(
            "Фильм1", "", "8,8", null, true,
            2015, MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            "Фильм2", "", "", null, false,
            2021, MovieEntity.Companion.MovieGenres.Comedy.genre,
            MovieEntity.Companion.MovieStates.Premiere.state
        ),
        MovieEntity(
            "Фильм8", "", "", null, true,
            2021, MovieEntity.Companion.MovieGenres.Comedy.genre,
            MovieEntity.Companion.MovieStates.Premiere.state
        ),
        MovieEntity(
            "Фильм9", "", "", null, false,
            2021, MovieEntity.Companion.MovieGenres.Comedy.genre,
            MovieEntity.Companion.MovieStates.Premiere.state
        ),
        MovieEntity(
            "Фильм10", "", "", null, false,
            2021, MovieEntity.Companion.MovieGenres.Comedy.genre,
            MovieEntity.Companion.MovieStates.Premiere.state
        ),
        MovieEntity(
            "Фильм11", "", "", null, false,
            2021, MovieEntity.Companion.MovieGenres.Comedy.genre,
            MovieEntity.Companion.MovieStates.Premiere.state
        ),
        MovieEntity(
            "Фильм3", "", "5,8", null, true,
            2013, MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.Other.state
        ),
        MovieEntity(
            "Фильм4", "", "", null, false,
            2023, MovieEntity.Companion.MovieGenres.Historical.genre,
            MovieEntity.Companion.MovieStates.ComingSoon.state
        ),
        MovieEntity(
            "Фильм5", "", "8,1", null, true,
            1997, MovieEntity.Companion.MovieGenres.Crime.genre,
            MovieEntity.Companion.MovieStates.TopRated.state
        ),
        MovieEntity(
            "Фильм6", "", "6,4", null, false,
            2004, MovieEntity.Companion.MovieGenres.Action.genre,
            MovieEntity.Companion.MovieStates.Other.state
        ),
        MovieEntity(
            "Фильм7", "", "", null, true,
            2021, MovieEntity.Companion.MovieGenres.ScienceFiction.genre,
            MovieEntity.Companion.MovieStates.Premiere.state
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
}