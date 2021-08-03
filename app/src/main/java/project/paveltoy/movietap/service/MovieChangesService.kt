package project.paveltoy.movietap.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import project.paveltoy.movietap.data.loader.TMDBMovieLoader
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private const val REQUEST_MOVIE_CHANGES_LIST = "/movie/changes"

class MovieChangesService : Service() {

    companion object {
        const val ACTION = "project.paveltoy.movietap.changes_movie_action"
        const val ACTION_TAG = "project.paveltoy.movietap.changes_movie_tag"
    }

    override fun onCreate() {
        Log.d("@@@", "$ACTION сервис onCreate")
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("@@@", "$ACTION сервис onStartCommand")
        loadMovieChangesList()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun loadMovieChangesList() {
        val uri = URL("${TMDBMovieLoader.URL_MAIN}$REQUEST_MOVIE_CHANGES_LIST${TMDBMovieLoader.API_KEY}")
        val handler = Handler(Looper.getMainLooper())
        Thread {
            lateinit var connection: HttpsURLConnection
            try {
                connection = uri.openConnection() as HttpsURLConnection
                connection.requestMethod = TMDBMovieLoader.REQUEST_METHOD_GET
                connection.readTimeout = TMDBMovieLoader.READ_TIMEOUT
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response =  reader.readText()
                if (response.isNotEmpty()) {
                    handler.post {
                        send(response)
                    }
                }
            } finally {
                connection.disconnect()
            }
        }.start()
    }

    private fun send(response: String) {
        val intent = Intent(ACTION)
        intent.putExtra(ACTION_TAG, response)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

}