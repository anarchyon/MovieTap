package project.paveltoy.movietap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import project.paveltoy.movietap.databinding.ActivityMainBinding
import project.paveltoy.movietap.ui.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, MainFragment())
            .commit()
    }
}