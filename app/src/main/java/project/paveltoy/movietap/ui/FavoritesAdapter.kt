package project.paveltoy.movietap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import project.paveltoy.movietap.R
import project.paveltoy.movietap.data.MovieEntity
import project.paveltoy.movietap.data.getTextForIsFavoriteSnackbar
import project.paveltoy.movietap.databinding.ItemFavoriteMovieBinding

class FavoritesAdapter(
    private val clickedMovieLiveData: MutableLiveData<MovieEntity>
) :
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
            bindMovie(movie)
            setListeners(movie)
        }

        private fun setListeners(movie: MovieEntity) {
            val index = data.indexOf(movie)
            itemView.setOnClickListener {
                clickedMovieLiveData.value = movie
                itemView.findNavController()
                    .navigate(R.id.action_favorite_movie_fragment_to_detailFragment)
            }
            itemFavoriteMovieBinding.movieFavoriteToggleButton.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                clickedMovieLiveData.value = movie
                val text = getTextForIsFavoriteSnackbar(itemView.resources, movie.name, movie.isFavorite)
                Snackbar.make(itemView.rootView, text, BaseTransientBottomBar.LENGTH_LONG)
                    .setAnchorView(R.id.bottom_navigation)
                    .show()
                notifyItemRemoved(index)
            }
        }

        private fun bindMovie(movie: MovieEntity) {
            itemFavoriteMovieBinding.movieImageView.setImageURI(movie.imageUrl)
            itemFavoriteMovieBinding.movieNameTextView.text = movie.name
            itemFavoriteMovieBinding.movieYearTextView.text = movie.movieYear.toString()
            itemFavoriteMovieBinding.movieGenresTextView.text = movie.movieGenre.toString()
            itemFavoriteMovieBinding.movieRateTextView.text = movie.rate
            itemFavoriteMovieBinding.movieFavoriteToggleButton.isChecked = movie.isFavorite
        }

    }
}
