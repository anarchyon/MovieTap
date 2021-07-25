package project.paveltoy.movietap

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import project.paveltoy.movietap.data.Section
import project.paveltoy.movietap.data.TMDBSections
import project.paveltoy.movietap.databinding.ActivityMainBinding
import project.paveltoy.movietap.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFERENCES_TAG = "movie_list_preferences"
        const val MOVIE_LIST_KEY = "movie_list_key"
    }

    lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            fillTMDBSections()
            loadPreferences()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            run {
                if (destination.id == R.id.detail_fragment) {
                    binding.bottomNavigation.visibility = View.GONE
                } else {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
            }
        }

//        requestPermissions(arrayOf(Manifest.permission.INTERNET), 1)
        requestPermissionLauncher.launch(Manifest.permission.INTERNET)

    }

    private fun loadPreferences() {
        val preferences = getSharedPreferences(PREFERENCES_TAG, MODE_PRIVATE)
        if (preferences.contains(MOVIE_LIST_KEY)) {

        } else {
            mainViewModel.setDefaultSectionsList()
        }
    }

    private fun fillTMDBSections() {
        TMDBSections.SECTIONS = listOf(
            Section(0, getString(R.string.subunit_now_playing), "/movie/now_playing"),
            Section(1, getString(R.string.subunit_upcoming), "/movie/upcoming"),
            Section(2, getString(R.string.subunit_top_rated), "/movie/top_rated"),
            Section(3, getString(R.string.subunit_popular), "/movie/popular"),
        )
    }
}
