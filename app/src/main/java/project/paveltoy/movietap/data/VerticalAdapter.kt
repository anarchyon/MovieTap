package project.paveltoy.movietap.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.databinding.ItemMovieSectionBinding

class VerticalAdapter() : RecyclerView.Adapter<VerticalAdapter.BaseViewHolder>() {
    var data: Map<Int, List<MovieEntity>> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemMovieSectionBinding = ItemMovieSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(itemMovieSectionBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val listOfKeys = data.keys
        holder.bind(listOfKeys.elementAt(position))
    }

    override fun getItemCount(): Int {
        return data.keys.size
    }

    inner class BaseViewHolder(private val itemMovieSectionBinding: ItemMovieSectionBinding) : RecyclerView.ViewHolder(itemMovieSectionBinding.root) {
        private lateinit var innerRecyclerView: RecyclerView
        private val innerAdapter = MovieAdapter()

        fun bind(key: Int) {
            itemMovieSectionBinding.sectionTitle.text = itemView.context.getString(key)
            innerRecyclerView = itemMovieSectionBinding.innerRecyclerView
            innerRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            innerRecyclerView.adapter = innerAdapter
            innerRecyclerView.isNestedScrollingEnabled = false
            innerAdapter.data = this@VerticalAdapter.data[key]!!
            innerAdapter.notifyDataSetChanged()
        }
    }
}