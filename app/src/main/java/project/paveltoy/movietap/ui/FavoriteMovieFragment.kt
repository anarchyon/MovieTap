package project.paveltoy.movietap.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.databinding.FragmentFavoriteMovieBinding
import project.paveltoy.movietap.databinding.FragmentMainBinding
import project.paveltoy.movietap.viewmodels.MainViewModel

class FavoriteMovieFragment : Fragment() {
    private var _binding: FragmentFavoriteMovieBinding? = null
    val binding get() = _binding!!
    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        adapter = FavoritesAdapter(viewModel.clickedMovieLiveData)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteRecyclerView = binding.favoriteRecyclerView
        favoriteRecyclerView.layoutManager = GridLayoutManager(context, 2)
        favoriteRecyclerView.adapter = adapter
        adapter.data = viewModel.getFavoriteMovies()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}