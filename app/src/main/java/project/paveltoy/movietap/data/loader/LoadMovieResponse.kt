package project.paveltoy.movietap.data.loader

import project.paveltoy.movietap.data.entity.MovieEntity

data class LoadMovieResponse(
    val page: Int,
    val results: List<MovieEntity>,
    val total_results: Int,
    val total_pages: Int,
)
