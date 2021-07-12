package project.paveltoy.movietap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import project.paveltoy.movietap.data.MovieEntity
import project.paveltoy.movietap.databinding.ActivityMainBinding
import project.paveltoy.movietap.ui.DetailFragment
import project.paveltoy.movietap.ui.MainFragment
import project.paveltoy.movietap.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val observer = Observer<MovieEntity>{
        showDetail()
    }

    private fun showDetail() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, DetailFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, MainFragment())
            .commit()

        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.clickedMovieLiveData.observe(this, observer)
    }
}