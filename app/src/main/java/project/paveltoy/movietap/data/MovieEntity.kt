package project.paveltoy.movietap.data

import android.net.Uri
import project.paveltoy.movietap.R
import java.util.*

data class MovieEntity(
    val name: String,
    val description: String,
    val rate: String,
    val imageUrl: Uri?,
    var isFavorite: Boolean,
    val movieYear: Int,
    val movieGenre: Int,
    val movieState: Int,
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val movie = o as MovieEntity
        return name.lowercase() == movie.name.lowercase() && movieYear == movie.movieYear
    }

    override fun hashCode(): Int {
        return Objects.hash(name, movieYear)
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
