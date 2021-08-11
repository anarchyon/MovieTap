package project.paveltoy.movietap.data.repository.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbMovieGenres(
    @PrimaryKey
    val id: Int,
    val name: String,
)