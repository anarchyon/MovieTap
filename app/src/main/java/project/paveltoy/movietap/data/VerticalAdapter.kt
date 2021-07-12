package project.paveltoy.movietap.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.databinding.ItemMovieSectionBinding

class VerticalAdapter(private val clickedMovieLiveData: MutableLiveData<MovieEntity>) :
    RecyclerView.Adapter<VerticalAdapter.BaseViewHolder>(), MovieAdapter.ItemClickListener {
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

    override fun getItemCount(): Int {
        return data.keys.size
    }

    override fun onMovieClick(movie: MovieEntity) {
        clickedMovieLiveData.value = movie
    }

    inner class BaseViewHolder(private val itemMovieSectionBinding: ItemMovieSectionBinding) :
        RecyclerView.ViewHolder(itemMovieSectionBinding.root) {

        private lateinit var innerRecyclerView: RecyclerView

        init {
            movieAdapter = MovieAdapter()
            movieAdapter.setItemClickListener(this@VerticalAdapter)
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