package project.paveltoy.movietap.data

import android.content.res.Resources
import project.paveltoy.movietap.R

//private fun prepareMoviesMap(movies: List<MovieEntity>): Map<Int, ArrayList<MovieEntity>> {
//    val map = hashMapOf<Int, ArrayList<MovieEntity>>()
//    val keys = MovieEntity.Companion.MovieStates.values()
//    for (key in keys) {
//        map[key.state] = arrayListOf()
//    }
//    for (movie in movies) {
//        map[movie.movieState]!!.add(movie)
//    }
//    return map
//}
//
//fun getSectionMovies(movies: List<MovieEntity>): Map<Int, ArrayList<MovieEntity>> {
//    return prepareMoviesMap(movies)
//}

fun getFavoriteMovies(movies: List<MovieEntity>): List<MovieEntity> =
    movies.filter { it.isFavorite }

fun getTextForIsFavoriteSnackbar(resources: Resources, movieName: String, isFavorite: Boolean): String {
    val appendPrologue = resources.getString(R.string.movie)
    val appendEpilogue = if (isFavorite)
        resources.getString(R.string.movie_favorite) else
        resources.getString(R.string.movie_not_favorite)
    return "$appendPrologue $movieName $appendEpilogue"
}
