package project.paveltoy.movietap.data

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
        const val URL_MAIN = "https://api.themoviedb.org/3"
        const val URL_GENRES_REQUEST = "/genre/movie/list"
        const val URL_API_KEY = "?api_key="
        const val URL_LANGUAGE = "&language="
        const val REQUEST_METHOD_GET = "GET"
    }

    override fun loadMovieList(){

    }

    override fun loadGenres(loadListener: (String) -> Unit) {
        val uri = URL("$URL_MAIN$URL_GENRES_REQUEST$URL_API_KEY${BuildConfig.TMDB_API_KEY}$URL_LANGUAGE${Locale.getDefault()}")
        val handler = Handler(Looper.getMainLooper())

        Thread {
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_METHOD_GET
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                loadListener(bufferedReader.readText())
            } catch (e: Exception) {
                Log.d("@@@", e.message?:"Все пропало")
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

    override fun loadSubunits(loadListener: (String) -> Unit) {

    }
}