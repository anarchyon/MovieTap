package project.paveltoy.movietap

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import project.paveltoy.movietap.data.Section
import project.paveltoy.movietap.data.TMDBSections
import project.paveltoy.movietap.databinding.ActivityMainBinding
import project.paveltoy.movietap.service.MovieChangesService
import project.paveltoy.movietap.viewmodels.MainViewModel

private const val PREFERENCES_TAG = "movie_list_preferences"
private const val MOVIE_LIST_KEY = "movie_list_key"
private const val PERMISSION_REQUEST_INTERNET = 1

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val changesMovieReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val response = p1?.getStringExtra(MovieChangesService.ACTION_TAG)
            processChangesMovieIntent(response)
        }

    }

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
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        fillTMDBSections()
        loadPreferences()
        val intent = Intent(this, MovieChangesService::class.java)
        startService(intent)
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

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(changesMovieReceiver)
        super.onDestroy()
    }
}
