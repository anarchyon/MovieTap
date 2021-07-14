package project.paveltoy.movietap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.data.MovieEntity
import project.paveltoy.movietap.databinding.ItemFavoriteMovieBinding

class FavoritesAdapter(clickedMovieLiveData: MutableLiveData<MovieEntity>) :
    RecyclerView.Adapter<FavoritesAdapter.BaseViewHolder>() {
    var data: List<MovieEntity> = ArrayList()

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
            itemFavoriteMovieBinding.movieImageView.setImageURI(movie.imageUrl)
            itemFavoriteMovieBinding.movieNameTextView.text = movie.name
            itemFavoriteMovieBinding.movieYearTextView.text = movie.movieYear.toString()
            itemFavoriteMovieBinding.movieGenresTextView.text = movie.movieGenre.toString()
            itemFavoriteMovieBinding.movieRateTextView.text = movie.rate
            itemFavoriteMovieBinding.movieFavoriteToggleButton.isChecked = movie.isFavorite
            itemFavoriteMovieBinding.movieFavoriteToggleButton.setOnClickListener {

            }
        }

    }
}
