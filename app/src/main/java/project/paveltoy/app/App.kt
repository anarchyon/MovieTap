package project.paveltoy.app

import android.app.Application
import androidx.room.Room
import project.paveltoy.movietap.data.repository.local.FavoriteDao
import project.paveltoy.movietap.data.repository.local.MoviesDataBase
import java.lang.IllegalStateException

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: MoviesDataBase? = null
        private const val DB_NAME = "Movies.db"

        fun getFavoriteDao(): FavoriteDao {
            if (db == null) {
                synchronized(MoviesDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating Database")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            MoviesDataBase::class.java,
                            DB_NAME
                        ).build()
                    }
                }
            }
            return db!!.favoriteDao()
        }
    }
}