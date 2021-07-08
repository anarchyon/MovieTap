package project.paveltoy.movietap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.data.FakeMovieRepo
import project.paveltoy.movietap.data.MoviePreparatory
import project.paveltoy.movietap.data.VerticalAdapter
import project.paveltoy.movietap.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainRecyclerView: RecyclerView
    private val sectionAdapter = VerticalAdapter()
    var movieRepo = FakeMovieRepo()

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
        mainRecyclerView = binding.movieRecyclerView
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mainRecyclerView.adapter = sectionAdapter
        val moviePreparatory = MoviePreparatory(movieRepo.getMovies())
        sectionAdapter.data = moviePreparatory.getSectionMovies()
        sectionAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}