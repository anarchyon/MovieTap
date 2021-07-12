package project.paveltoy.movietap.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.paveltoy.movietap.data.MovieEntity

class MainViewModel : ViewModel() {
    var clickedMovieLiveData = MutableLiveData<MovieEntity>()
}