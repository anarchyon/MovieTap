package project.paveltoy.movietap.data

import kotlin.reflect.KFunction2

interface MovieLoader {
    fun loadMovieBySection(key: String, request: String, loadListener: (String, String?) -> Unit)
    fun loadMovieByGenre(key: String, genreId: Int, loadListener: (String, String?) -> Unit)
    fun loadGenres(loadListener: (String, String?) -> Unit)
}
