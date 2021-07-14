package project.paveltoy.movietap.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.databinding.FragmentMainBinding
import project.paveltoy.movietap.viewmodels.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel
    private lateinit var verticalAdapter: VerticalAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        verticalAdapter = VerticalAdapter(viewModel.clickedMovieLiveData)
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
        mainRecyclerView = binding.movieRecyclerView
        mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mainRecyclerView.adapter = verticalAdapter
        verticalAdapter.data = viewModel.getMovies()
        verticalAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}