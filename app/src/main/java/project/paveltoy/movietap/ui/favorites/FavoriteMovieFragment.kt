package project.paveltoy.movietap.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import project.paveltoy.movietap.R
import project.paveltoy.movietap.data.entity.MovieEntity
import project.paveltoy.movietap.data.getTextForIsFavoriteSnackbar
import project.paveltoy.movietap.databinding.FragmentFavoriteMovieBinding
import project.paveltoy.movietap.ui.MainViewModel

class FavoriteMovieFragment : Fragment() {
    private var _binding: FragmentFavoriteMovieBinding? = null
    val binding get() = _binding!!
    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var data: List<MovieEntity>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        adapter = FavoritesAdapter()
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
        serRecyclerView()
        setAdapter()
    }

    private fun serRecyclerView() {
        favoriteRecyclerView = binding.favoriteRecyclerView
        favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        favoriteRecyclerView.adapter = adapter
    }

    private fun setAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteMovies()
        }
        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }
        setOnItemClickListener()
        setOnFavoriteChanged()
    }

    private fun setOnItemClickListener() {
        adapter.onItemClick = {
            viewModel.clickedMovieLiveData.value = it
            findNavController().navigate(R.id.action_favorite_movie_fragment_to_detailFragment)
        }
    }

    private fun setOnFavoriteChanged() {
        adapter.onFavoriteChanged = { movie: MovieEntity, index: Int ->
            movie.isFavorite = !movie.isFavorite
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.removeFromFavorite(movie)
            }
            val text = getTextForIsFavoriteSnackbar(resources, movie.title, movie.isFavorite)
            Snackbar.make(requireView(), text, BaseTransientBottomBar.LENGTH_LONG)
                .setAnchorView(R.id.bottom_navigation)
                .show()
            adapter.notifyItemRemoved(index)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}