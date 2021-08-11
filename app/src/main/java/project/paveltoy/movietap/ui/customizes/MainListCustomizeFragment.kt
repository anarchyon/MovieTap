package project.paveltoy.movietap.ui.customizes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.GridLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import project.paveltoy.movietap.R
import project.paveltoy.movietap.data.entity.TMDBSections
import project.paveltoy.movietap.databinding.FragmentMainListCustomizeBinding
import project.paveltoy.movietap.ui.MainViewModel

class MainListCustomizeFragment : Fragment() {
    private var _binding: FragmentMainListCustomizeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainListCustomizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSections()
        binding.saveSelectedSectionsButton.setOnClickListener {
            val sectionsForDisplay = SectionsForDisplay()
            binding.gridLayout.forEach {
                if (it is CheckBox) {
                    val sectionName = it.text.toString()
                    sectionsForDisplay.sections[sectionName] = it.isChecked
                }
            }
            viewModel.setSectionsList(sectionsForDisplay)
            viewModel.callbackToSavePrefs?.invoke(sectionsForDisplay)
        }
    }

    private fun showSections() {
        val sectionForDisplay = viewModel.getSectionsForDisplay()
        sectionForDisplay.sections.let { sectionMap ->
            sectionMap.keys.forEach {
                val param = GridLayout.LayoutParams()
                param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                val checkBox = CheckBox(context)
                checkBox.apply {
                    text = it
                    isChecked = sectionMap[it]!!
                    layoutParams = param
                }
                binding.gridLayout.addView(checkBox)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}