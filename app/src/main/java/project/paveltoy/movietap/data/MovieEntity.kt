package project.paveltoy.movietap.data

import android.net.Uri
import project.paveltoy.movietap.R
import java.util.*

data class MovieEntity(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val poster_path: Uri?,
    var isFavorite: Boolean,
    val release_date: String,
    val overview: String,
    val movieGenres: List<Int>,
    val movieState: Int,
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val movie = o as MovieEntity
        return id == movie.id
    }

    override fun hashCode(): Int {
        return Objects.hash(title, release_date)
    }

    companion object {
        fun generateId(): String {
            return UUID.randomUUID().toString()
        }

        enum class MovieGenres(val genre: Int) {
            Action(R.string.genre_action),
            Animation(R.string.genre_animation),
            Comedy(R.string.genre_comedy),
            Crime(R.string.genre_crime),
            Drama(R.string.genre_drama),
            Fantasy(R.string.genre_fantasy),
            Historical(R.string.genre_historical),
            Horror(R.string.genre_horror),
            Romance(R.string.genre_romance),
            ScienceFiction(R.string.genre_science_fiction),
            Thriller(R.string.genre_thriller),
            Western(R.string.genre_western),
            Other(R.string.genre_other),
        }

        enum class MovieStates(val state: Int) {
            TopRated(R.string.state_top_rated),
            Premiere(R.string.state_premiere),
            ComingSoon(R.string.state_coming_soon),
            Other(R.string.state_other)
        }
    }
}
