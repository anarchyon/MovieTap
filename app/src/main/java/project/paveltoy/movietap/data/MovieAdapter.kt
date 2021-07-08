package project.paveltoy.movietap.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.R
import project.paveltoy.movietap.databinding.ItemMovieBinding

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.BaseViewHolder>() {

    var data: List<MovieEntity> = ArrayList()
//        set(value) = if (value != null) field = value else field = ArrayList()

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

    inner class BaseViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun bind(movie: MovieEntity) {
            itemMovieBinding.movieName.text = movie.name
            itemMovieBinding.movieYear.text = movie.movieYear.toString()
            itemMovieBinding.movieRate.text = movie.rate
            itemMovieBinding.movieImage.setImageURI(movie.imageUrl)
            itemMovieBinding.movieFavoriteToggle.isChecked = movie.isFavorite
        }

    }
}