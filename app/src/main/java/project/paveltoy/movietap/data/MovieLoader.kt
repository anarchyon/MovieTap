package project.paveltoy.movietap.data

interface MovieLoader {
    fun loadMovieList()
    fun loadGenres(loadListener: (String) -> Unit)
    fun loadSubunits(loadListener: (String) -> Unit)
}
