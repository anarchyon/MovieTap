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
import project.paveltoy.movietap.databinding.ItemMovieBinding

class MovieAdapter(private val clickedMovieLiveData: MutableLiveData<MovieEntity>) :
    RecyclerView.Adapter<MovieAdapter.BaseViewHolder>() {
    var data: List<MovieEntity> = ArrayList()

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
            setListener(movie)
        }

        private fun bindMovie(movie: MovieEntity) {
            itemMovieBinding.movieNameTextView.text = movie.name
            itemMovieBinding.movieYearTextView.text = movie.movieYear.toString()
            itemMovieBinding.movieRateTextView.text = movie.rate
            itemMovieBinding.movieImageView.setImageURI(movie.imageUrl)
            itemMovieBinding.movieFavoriteToggleButton.isChecked = movie.isFavorite
        }

        private fun setListener(movie: MovieEntity) {
            itemView.setOnClickListener {
                clickedMovieLiveData.value = movie
                itemView.findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
            }
            itemMovieBinding.movieFavoriteToggleButton.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                val text = getTextForIsFavoriteSnackbar(itemView.resources, movie.name, movie.isFavorite)
                Snackbar.make(itemView.rootView, text, BaseTransientBottomBar.LENGTH_LONG)
                    .setAnchorView(R.id.bottom_navigation)
                    .show()
            }
        }

    }
}