package project.paveltoy.movietap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import project.paveltoy.movietap.R
import project.paveltoy.movietap.data.MovieEntity
import project.paveltoy.movietap.databinding.ItemMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.BaseViewHolder>() {
    var data: List<MovieEntity> = ArrayList()
    private var itemClickListener : ItemClickListener? = null

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun interface ItemClickListener {
        fun onMovieClick(movie: MovieEntity)
    }

    inner class BaseViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun bind(movie: MovieEntity) {
            setListener(movie)
            bindMovie(movie)
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
                itemClickListener?.onMovieClick(movie)
            }
            itemMovieBinding.movieFavoriteToggleButton.setOnClickListener {
                Snackbar.make(itemView.rootView, movie.isFavorite.toString(), BaseTransientBottomBar.LENGTH_LONG)
                    .setAnchorView(R.id.bottom_navigation)
                    .show()
            }
        }

    }
}