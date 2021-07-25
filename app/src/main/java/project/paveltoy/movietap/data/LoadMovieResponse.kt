package project.paveltoy.movietap.data

data class LoadMovieResponse(
    val page: Int,
    val results: List<MovieEntity>,
    val total_results: Int,
    val total_pages: Int,
)
