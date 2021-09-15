package project.paveltoy.movietap.ui

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import project.paveltoy.movietap.R
import project.paveltoy.movietap.data.entity.Section
import project.paveltoy.movietap.data.entity.TMDBSections
import project.paveltoy.movietap.databinding.ActivityMainBinding
//import project.paveltoy.movietap.service.MovieChangesService
import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

private const val PREFERENCES_TAG = "movie_list_preferences"
private const val MOVIE_LIST_KEY = "movie_list_key"
private const val PERMISSION_REQUEST_INTERNET = 1
private const val PERMISSION_REQUEST_LOCATION = 2

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var navController: NavController
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.callbackToSavePrefs = this::savePreferences
        mainViewModel.movieGenres.observe(this) {
            loadPreferences()
        }
        mainViewModel.sectionsForDisplayLiveData.observe(this) {
            mainViewModel.getMovies()
        }
        preferences = getSharedPreferences(PREFERENCES_TAG, MODE_PRIVATE)

        checkInternetPermission()
    }

    private fun checkInternetPermission() {
        if (checkSelfPermission(Manifest.permission.INTERNET)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startLoadData()
        } else {
            requestInternetPermission()
        }
    }

    private fun startLoadData() {
        setNavigation()
        fillTMDBSections()
        mainViewModel.setMainSections()
        mainViewModel.getGenres()
    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detail_fragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    private fun requestInternetPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.INTERNET
            ), PERMISSION_REQUEST_INTERNET
        )
    }

    private fun loadPreferences() {
        if (preferences.contains(MOVIE_LIST_KEY)) {
            val sectionsForDisplay = Gson().fromJson(
                preferences.getString(MOVIE_LIST_KEY, null),
                SectionsForDisplay::class.java
            )
            mainViewModel.setSectionsList(sectionsForDisplay)
        } else mainViewModel.setSectionsList(null)
    }

    private fun savePreferences(sectionsForDisplay: SectionsForDisplay) {
        val sectionForDisplayString = Gson().toJson(sectionsForDisplay)
        preferences.edit().putString(MOVIE_LIST_KEY, sectionForDisplayString).apply()
    }

    private fun fillTMDBSections() {
        TMDBSections.SECTIONS = listOf(
            Section(0, getString(R.string.subunit_now_playing), "now_playing"),
            Section(1, getString(R.string.subunit_upcoming), "upcoming"),
            Section(2, getString(R.string.subunit_top_rated), "top_rated"),
            Section(3, getString(R.string.subunit_popular), "popular"),
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_INTERNET) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLoadData()
            }
        }
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openMaps()
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.topAppbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.contacts -> {
                navController.navigate(R.id.action_to_contacts_fragment)
                true
            }
            R.id.maps -> {
                checkLocationPermission()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun checkLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            openMaps()
        } else {
            requestLocationPermission()
        }
    }

    private fun openMaps() {
        navController.navigate(R.id.action_to_map_fragment)
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_LOCATION
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
