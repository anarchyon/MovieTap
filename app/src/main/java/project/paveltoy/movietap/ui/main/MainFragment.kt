package project.paveltoy.movietap.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import project.paveltoy.movietap.R
import project.paveltoy.movietap.data.getTextForIsFavoriteSnackbar
import project.paveltoy.movietap.databinding.FragmentMainBinding
import project.paveltoy.movietap.ui.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var movieSectionsAdapter: MovieSectionsAdapter
    private var movieAdapters = hashMapOf<String, MovieAdapter>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setAdapter()
    }

    private fun setRecyclerView() {
        mainRecyclerView = binding.verticalRecyclerView
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setAdapter() {
        viewModel.movieSectionsLiveData.observe(viewLifecycleOwner) {
            val dataSet = viewModel.getMovieSections()
            setMovieAdapters(dataSet)
            movieSectionsAdapter = MovieSectionsAdapter(movieAdapters)
            movieSectionsAdapter.data = it
            movieSectionsAdapter.notifyDataSetChanged()
            mainRecyclerView.adapter = movieSectionsAdapter
            setOnItemClickListener()
            setOnFavoriteChanged()
        }
    }

    private fun setMovieAdapters(keys: Set<String>) {
        keys.forEach { key ->
            val movieAdapter = MovieAdapter()
            viewModel.liveDataSectionMovieList[key]?.observe(viewLifecycleOwner) {
                it?.let {
                    movieAdapter.data = it
                    movieAdapter.notifyDataSetChanged()
                }
            }
            movieAdapters[key] = movieAdapter
        }
    }

    private fun setOnItemClickListener() {
        movieSectionsAdapter.onItemClick = {
            viewModel.clickedMovieLiveData.value = it
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        }
    }

    private fun setOnFavoriteChanged() {
        movieSectionsAdapter.onFavoriteChanged = {
            it.isFavorite = !it.isFavorite
            if (it.isFavorite) {
                viewModel.addToFavorite(it)
            } else {
                viewModel.removeFromFavorite(it)
            }
            val text = getTextForIsFavoriteSnackbar(resources, it.title, it.isFavorite)
            Snackbar.make(requireView(), text, BaseTransientBottomBar.LENGTH_LONG)
                .setAnchorView(R.id.bottom_navigation)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}