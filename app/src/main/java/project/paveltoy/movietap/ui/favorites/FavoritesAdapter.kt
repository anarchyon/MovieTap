package project.paveltoy.movietap.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.databinding.ItemFavoriteMovieBinding

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.BaseViewHolder>() {
    var data: List<MovieEntity> = ArrayList()
    lateinit var onItemClick: (movie: MovieEntity) -> Unit
    lateinit var onFavoriteChanged: (movie: MovieEntity, index: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemFavoriteMovieBinding =
            ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(itemFavoriteMovieBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class BaseViewHolder(private val itemFavoriteMovieBinding: ItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(itemFavoriteMovieBinding.root) {

        fun bind(movie: MovieEntity) {
            bindMovie(movie)
            setListeners(movie)
        }

        private fun setListeners(movie: MovieEntity) {
            itemView.setOnClickListener {
                onItemClick(movie)
            }
            itemFavoriteMovieBinding.movieFavoriteToggleButton.setOnClickListener {
                onFavoriteChanged(movie, data.indexOf(movie))
            }
        }

        private fun bindMovie(movie: MovieEntity) {
            itemFavoriteMovieBinding.apply {
//                movieImageView.setImageURI(movie.poster_path)
                movieNameTextView.text = movie.title
                movieYearTextView.text = movie.release_date
                movieGenresTextView.text = movie.movieGenres.toString()
                movieRateTextView.text = movie.vote_average.toString()
                movieFavoriteToggleButton.isChecked = movie.isFavorite
            }
        }

    }
}
