package project.paveltoy.movietap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import project.paveltoy.movietap.data.MovieEntity
import project.paveltoy.movietap.databinding.FragmentDetailBinding
import project.paveltoy.movietap.viewmodels.MainViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: MovieEntity
    private val observer = Observer<MovieEntity> {
        movie = it
        updateInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.clickedMovieLiveData.observe(viewLifecycleOwner, observer)
    }

    private fun updateInfo() {
        binding.apply {
            movieDescriptionDetailFragmentTextView.text = movie.description
            movieNameDetailFragmentTextView.text = movie.name
            movieYearFragmentDetailTextView.text = movie.movieYear.toString()
            movieGenreDetailFragmentTextView.text = resources.getString(movie.movieGenre)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
