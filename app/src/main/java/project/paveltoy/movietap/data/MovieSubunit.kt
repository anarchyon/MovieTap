package project.paveltoy.movietap.data

import project.paveltoy.movietap.R


data class MovieSubunit(
    val subunits: Map<String, Int> = mapOf(
        "now_playing" to R.string.subunit_now_playing,
        "upcoming" to R.string.subunit_upcoming,
        "top_rated" to R.string.subunit_top_rated,
        "popular" to R.string.subunit_popular,
        "latest" to R.string.subunit_latest,
    ),
    val genres: Map<Int, String>
)