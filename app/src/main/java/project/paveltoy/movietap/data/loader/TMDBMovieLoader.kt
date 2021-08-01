package project.paveltoy.movietap.data.loader

import android.os.Handler
import android.os.Looper
import android.util.Log
import project.paveltoy.movietap.BuildConfig
import project.paveltoy.movietap.data.entity.MovieGenres
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class TMDBMovieLoader : RetrofitMovieLoader {
    companion object {
        const val REQUEST_METHOD_GET = "GET"
        const val URL_MAIN = "https://api.themoviedb.org/3"
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
//        val uri =
//            URL("$URL_MAIN$request${this@Companion.API_KEY}$URL_LANGUAGE${Locale.getDefault()}$URL_REGION${Locale.getDefault().country}")
//        loadFrom(key, uri, loadListener)
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
//        val uri =
//            URL("$URL_MAIN$URL_REQUEST_MOVIES_BY${this@Companion.API_KEY}$URL_LANGUAGE${Locale.getDefault()}$URL_REGION${Locale.getDefault().country}$URL_GENRE$genreId")
//        loadFrom(key, uri, loadListener)
    }

    override fun loadGenres(callback: Callback<MovieGenres>) {
        tmdbServerAPI.getGenres(
            API_KEY,
            Locale.getDefault().toLanguageTag()
        ).enqueue(callback)
//        val uri =
//            URL("$URL_MAIN$URL_REQUEST_GENRES${this@Companion.API_KEY}$URL_LANGUAGE${Locale.getDefault()}")
//        loadFrom(null, uri, loadListener)
    }

    private fun loadFrom(key: String?, uri: URL, loadListener: (String, String?) -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_METHOD_GET
                urlConnection.readTimeout = READ_TIMEOUT
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val response = bufferedReader.readText()
                handler.post { loadListener(response, key) }
            } catch (e: Exception) {
                Log.d("@@@", e.message ?: "Все пропало")
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }
}