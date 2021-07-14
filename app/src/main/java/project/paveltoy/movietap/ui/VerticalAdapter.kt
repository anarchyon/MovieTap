package project.paveltoy.movietap.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.data.MovieEntity
import project.paveltoy.movietap.databinding.ItemMovieSectionBinding

class VerticalAdapter(private val clickedMovieLiveData: MutableLiveData<MovieEntity>) :
    RecyclerView.Adapter<VerticalAdapter.BaseViewHolder>() {
    var data: Map<Int, List<MovieEntity>> = HashMap()
    lateinit var movieAdapter: MovieAdapter

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
            movieAdapter.setItemClickListener { movie -> clickedMovieLiveData.value = movie }
        }

        fun bind(key: Int) {
            itemMovieSectionBinding.sectionTitleTextView.text = itemView.context.getString(key)
            innerRecyclerView = itemMovieSectionBinding.innerRecyclerView
            innerRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            innerRecyclerView.adapter = movieAdapter
            innerRecyclerView.isNestedScrollingEnabled = false
            movieAdapter.data = this@VerticalAdapter.data[key]!!
            movieAdapter.notifyDataSetChanged()
        }
    }
}