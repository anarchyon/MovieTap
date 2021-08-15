package project.paveltoy.movietap.data.repository.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovies(
    @PrimaryKey
    val id: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    var poster_path: String?,
    var isFavorite: Boolean,
    val release_date: String,
    val overview: String,
    val movieGenres: String,
    val movieState: Int,
)
