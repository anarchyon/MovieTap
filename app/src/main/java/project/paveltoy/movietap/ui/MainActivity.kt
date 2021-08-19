package project.paveltoy.movietap.ui

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import project.paveltoy.movietap.R
import project.paveltoy.movietap.data.entity.Section
import project.paveltoy.movietap.data.entity.TMDBSections
import project.paveltoy.movietap.databinding.ActivityMainBinding
import project.paveltoy.movietap.service.MovieChangesService
import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

private const val PREFERENCES_TAG = "movie_list_preferences"
private const val MOVIE_LIST_KEY = "movie_list_key"
private const val PERMISSION_REQUEST_INTERNET = 1

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var navController: NavController
    private val changesMovieReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val response = p1?.getStringExtra(MovieChangesService.ACTION_TAG)
            processChangesMovieIntent(response)
        }
    }
    lateinit var mainViewModel: MainViewModel

    private fun processChangesMovieIntent(response: String?) {
        Snackbar.make(
            binding.fragmentContainerMain,
            R.string.changes_movie_found, Snackbar.LENGTH_LONG
        ).setAnchorView(binding.bottomNavigation).show()
    }

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

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(changesMovieReceiver, IntentFilter(MovieChangesService.ACTION))

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
//        val intent = Intent(this, MovieChangesService::class.java)
//        startService(intent)
    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        navController = navHostFragment.navController
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
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLoadData()
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
        return when(item.itemId) {
            R.id.contacts -> {
                navController.navigate(R.id.action_to_contacts_fragment)
                true
            }
            else -> {false}
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(changesMovieReceiver)
        super.onDestroy()
    }
}
