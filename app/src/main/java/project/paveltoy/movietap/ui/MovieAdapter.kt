package project.paveltoy.movietap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.data.MovieEntity
import project.paveltoy.movietap.databinding.ItemMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.BaseViewHolder>() {
    var data: List<MovieEntity> = ArrayList()
    lateinit var onItemClick: (movie: MovieEntity) -> Unit
    lateinit var onFavoriteChanged: (movie: MovieEntity) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class BaseViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun bind(movie: MovieEntity) {
            bindMovie(movie)
            setListeners(movie)
        }

        private fun bindMovie(movie: MovieEntity) {
            itemMovieBinding.apply {
                movieNameTextView.text = movie.title
                movieYearTextView.text = movie.release_date.toString()
                movieRateTextView.text = movie.vote_average
                movieImageView.setImageURI(movie.poster_path)
                movieFavoriteToggleButton.isChecked = movie.isFavorite
            }
        }

        private fun setListeners(movie: MovieEntity) {
            itemView.setOnClickListener {
                onItemClick(movie)
            }
            itemMovieBinding.movieFavoriteToggleButton.setOnClickListener {
                onFavoriteChanged(movie)
            }
        }

    }
}