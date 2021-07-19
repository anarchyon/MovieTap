package project.paveltoy.movietap.data

private fun prepareMoviesMap(movies: List<MovieEntity>): Map<Int, ArrayList<MovieEntity>> {
    val map = hashMapOf<Int, ArrayList<MovieEntity>>()
    val keys = MovieEntity.Companion.MovieStates.values()
    for (key in keys) {
        map[key.state] = arrayListOf()
    }
    for (movie in movies) {
        map[movie.movieState]!!.add(movie)
    }
    return map
}

fun getSectionMovies(movies: List<MovieEntity>): Map<Int, ArrayList<MovieEntity>> {
    return prepareMoviesMap(movies)
}

fun getFavoriteMovies(movies: List<MovieEntity>): List<MovieEntity> =
    movies.filter { it.isFavorite }
