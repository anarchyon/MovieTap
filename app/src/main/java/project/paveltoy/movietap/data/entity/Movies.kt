package project.paveltoy.movietap.data.entity

import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

class Movies {
    var movieGenres = MovieGenres()
    var sectionsForDisplay = SectionsForDisplay()
    var movieSet = mutableMapOf<String, List<MovieEntity>>()

}