package project.paveltoy.movietap.data

class MoviePreparatory(private val movies: List<MovieEntity>) {
    val map = hashMapOf<Int, ArrayList<MovieEntity>>()

    private fun prepareMoviesMap() {
        val keys = MovieEntity.Companion.MovieStates.values()
        for (key in keys) {
            map[key.state] = arrayListOf()
        }
        for (movie in movies) {
            map[movie.movieState]!!.add(movie)
        }
    }

    fun getSectionMovies(): Map<Int, ArrayList<MovieEntity>> {
        prepareMoviesMap()
        return map
    }

}
