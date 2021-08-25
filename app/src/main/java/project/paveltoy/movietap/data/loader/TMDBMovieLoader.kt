package project.paveltoy.movietap.data.loader

import project.paveltoy.movietap.BuildConfig
import project.paveltoy.movietap.data.entity.MovieGenres
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class TMDBMovieLoader : RetrofitMovieLoader {
    companion object {
        const val REQUEST_METHOD_GET = "GET"
        const val URL_MAIN = "https://api.themoviedb.org/3/"
        const val IMAGE_SECURE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_LOGO_SIZE = "w300/"
        const val IMAGE_ORIGINAL_SIZE = "original"
        const val API_KEY = BuildConfig.TMDB_API_KEY
        const val READ_TIMEOUT = 10_000
    }

    private val tmdbServerAPI = Retrofit.Builder()
        .baseUrl(URL_MAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(TMDBServerAPI::class.java)

    override fun loadMovieBySection(
        key: String,
        request: String,
        callback: Callback<LoadMovieResponse>
    ) {
        tmdbServerAPI.getMoviesBySection(
            request,
            API_KEY,
            Locale.getDefault().toLanguageTag(),
            Locale.getDefault().country,
            1
        ).enqueue(callback)
    }

    override fun loadMovieByGenre(
        key: String,
        genreId: Int,
        callback: Callback<LoadMovieResponse>
    ) {
        tmdbServerAPI.getMoviesByGenre(
            API_KEY,
            Locale.getDefault().toLanguageTag(),
            Locale.getDefault().country,
            1,
            genreId
        ).enqueue(callback)
    }

    override fun loadGenres(callback: Callback<MovieGenres>) {
        tmdbServerAPI.getGenres(
            API_KEY,
            Locale.getDefault().toLanguageTag()
        ).enqueue(callback)
    }

    fun completePosterPath(posterPath: String): String {
        return "$IMAGE_SECURE_BASE_URL$IMAGE_LOGO_SIZE$posterPath?api_key=$API_KEY"
    }
}