package project.paveltoy.movietap

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import project.paveltoy.movietap.databinding.ActivityMainBinding
import project.paveltoy.movietap.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
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

        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initMovieSubunitList()
    }

    private fun initMovieSubunitList() {
        loadMovieSubunitList()
    }

    private fun loadMovieSubunitList() {

    }
}