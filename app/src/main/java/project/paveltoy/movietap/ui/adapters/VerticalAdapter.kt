package project.paveltoy.movietap.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.data.MovieEntity
import project.paveltoy.movietap.databinding.ItemMovieSectionBinding

class VerticalAdapter : RecyclerView.Adapter<VerticalAdapter.BaseViewHolder>() {
    var data: Map<String, List<MovieEntity>> = HashMap()
    lateinit var movieAdapter: MovieAdapter
    lateinit var onItemClick: (movie: MovieEntity) -> Unit
    lateinit var onFavoriteChanged: (movie: MovieEntity) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemMovieSectionBinding =
            ItemMovieSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(itemMovieSectionBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val listOfKeys = data.keys
        holder.bind(listOfKeys.elementAt(position))
    }

    override fun getItemCount() = data.keys.size

    inner class BaseViewHolder(private val itemMovieSectionBinding: ItemMovieSectionBinding) :
        RecyclerView.ViewHolder(itemMovieSectionBinding.root) {

        private lateinit var innerRecyclerView: RecyclerView

        init {
            movieAdapter = MovieAdapter()
        }

        fun bind(key: String) {
            itemMovieSectionBinding.sectionTitleTextView.text = key
            setRecyclerView()
            setAdapter(key)
        }

        private fun setRecyclerView() {
            innerRecyclerView = itemMovieSectionBinding.innerRecyclerView
            innerRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            innerRecyclerView.adapter = movieAdapter
            innerRecyclerView.isNestedScrollingEnabled = false
        }

        private fun setAdapter(key: String) {
            movieAdapter.data = this@VerticalAdapter.data[key]!!
            movieAdapter.notifyDataSetChanged()
            movieAdapter.onItemClick = onItemClick
            movieAdapter.onFavoriteChanged = onFavoriteChanged
        }
    }
}