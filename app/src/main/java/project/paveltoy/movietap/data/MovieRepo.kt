package project.paveltoy.movietap.data

interface MovieRepo {
    fun addMovie(movie: MovieEntity)
    fun getMovies(): List<MovieEntity>
    fun updateMovie(movie: MovieEntity)
    fun deleteMovie(movie: MovieEntity)
}