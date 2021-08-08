package project.paveltoy.movietap.data.repository.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieGenres(
    @PrimaryKey
    val id: Int,
    val name: String,
)