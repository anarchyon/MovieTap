package project.paveltoy.movietap.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class GenresService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        loadGenres()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun loadGenres() {

    }

}