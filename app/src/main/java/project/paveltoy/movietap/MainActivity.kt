package project.paveltoy.movietap

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import project.paveltoy.movietap.data.Section
import project.paveltoy.movietap.data.TMDBSections
import project.paveltoy.movietap.databinding.ActivityMainBinding
import project.paveltoy.movietap.viewmodels.MainViewModel

private const val PREFERENCES_TAG = "movie_list_preferences"
private const val MOVIE_LIST_KEY = "movie_list_key"
private const val PERMISSION_REQUEST_INTERNET = 1

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInternetPermission()
    }

    private fun checkInternetPermission() {
        if (checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            startLoadData()
        } else {
            requestInternetPermission()
        }
    }

    private fun startLoadData() {
        setNavigation()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        fillTMDBSections()
        loadPreferences()
    }

    private fun setNavigation() {
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
    }

    private fun requestInternetPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)) {
            requestPermissions(arrayOf(Manifest.permission.INTERNET), PERMISSION_REQUEST_INTERNET)
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_INTERNET) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLoadData()
            }
        }
    }
}
