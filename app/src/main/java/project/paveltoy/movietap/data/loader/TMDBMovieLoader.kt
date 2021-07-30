package project.paveltoy.movietap.data.loader

import android.os.Handler
import android.os.Looper
import android.util.Log
import project.paveltoy.movietap.BuildConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class TMDBMovieLoader : MovieLoader {
    companion object {
        const val REQUEST_METHOD_GET = "GET"
        const val URL_MAIN = "https://api.themoviedb.org/3"
        const val URL_REQUEST_GENRES = "/genre/movie/list"
        const val URL_REQUEST_MOVIES_BY = "/discover/movie"
        const val URL_GENRE = "&with_genres="
        const val URL_REQUEST_SEARCH_MOVIE = "/search/movie"
        const val URL_API_KEY = "?api_key=${BuildConfig.TMDB_API_KEY}"
        const val URL_LANGUAGE = "&language="
        const val URL_REGION = "&region="
        const val READ_TIMEOUT = 10_000
    }

    override fun loadMovieBySection(
        key: String,
        request: String,
        loadListener: (String, String?) -> Unit
    ) {
        val uri = URL("$URL_MAIN$request$URL_API_KEY$URL_LANGUAGE${Locale.getDefault()}$URL_REGION${Locale.getDefault().country}")
        loadFrom(key, uri, loadListener)
    }

    override fun loadMovieByGenre(
        key: String,
        genreId: Int,
        loadListener: (String, String?) -> Unit
    ) {
        val uri = URL("$URL_MAIN$URL_REQUEST_MOVIES_BY$URL_API_KEY$URL_LANGUAGE${Locale.getDefault()}$URL_REGION${Locale.getDefault().country}$URL_GENRE$genreId")
        loadFrom(key, uri, loadListener)
    }

    override fun loadGenres(loadListener: (String, String?) -> Unit) {
        val uri = URL("$URL_MAIN$URL_REQUEST_GENRES$URL_API_KEY$URL_LANGUAGE${Locale.getDefault()}")
        loadFrom(null, uri, loadListener)
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
                Log.d("@@@", e.message?:"Все пропало")
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }
}