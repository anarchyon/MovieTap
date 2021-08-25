package project.paveltoy.movietap.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.databinding.ItemMovieSectionBinding

class MovieSectionsAdapter(private val movieAdapters: HashMap<String, MovieAdapter>) :
    RecyclerView.Adapter<MovieSectionsAdapter.BaseViewHolder>() {
    var data: Set<String> = setOf()
    lateinit var onItemClick: (movie: MovieEntity) -> Unit
    lateinit var onFavoriteChanged: (movie: MovieEntity) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemMovieSectionBinding =
            ItemMovieSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(itemMovieSectionBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data.elementAt(position))
    }

    override fun getItemCount() = data.size

    inner class BaseViewHolder(private val itemMovieSectionBinding: ItemMovieSectionBinding) :
        RecyclerView.ViewHolder(itemMovieSectionBinding.root) {

        private lateinit var innerRecyclerView: RecyclerView

        fun bind(key: String) {
            itemMovieSectionBinding.sectionTitleTextView.text = key
            setRecyclerView(key)
            setAdapter(key)
        }

        private fun setRecyclerView(key: String) {
            innerRecyclerView = itemMovieSectionBinding.innerRecyclerView
            innerRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            innerRecyclerView.adapter = movieAdapters[key]
            innerRecyclerView.isNestedScrollingEnabled = false
        }

        private fun setAdapter(key: String) {
            movieAdapters[key]?.onItemClick = onItemClick
            movieAdapters[key]?.onFavoriteChanged = onFavoriteChanged
        }
    }
}