package project.paveltoy.movietap.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class MovieEntity(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    var poster_path: String,
    var isFavorite: Boolean,
    val release_date: String,
    val overview: String,
    val genre_ids: List<Int> = arrayListOf(),
    val movieState: Int,
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val movie = o as MovieEntity
        return id == movie.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    companion object {
        fun generateId(): String {
            return UUID.randomUUID().toString()
        }
    }
}
