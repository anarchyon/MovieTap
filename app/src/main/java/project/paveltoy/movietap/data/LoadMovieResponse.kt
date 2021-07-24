package project.paveltoy.movietap.data

data class LoadMovieResponse(
    val page: Int,
    val result: List<MovieEntity>,
    val total_results: Int,
    val total_pages: Int,
)
