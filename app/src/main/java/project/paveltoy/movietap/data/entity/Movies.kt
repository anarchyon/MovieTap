package project.paveltoy.movietap.data.entity

import project.paveltoy.movietap.ui.customizes.SectionsForDisplay

class Movies {
    val sections: Sections = TMDBSections
    var movieGenres = MovieGenres()
    var sectionsForDisplay = SectionsForDisplay()
    var movieSectionsList: List<String> = listOf()
    var movieGenresList: List<String> = listOf()
    var movieSet = hashMapOf<String, List<MovieEntity>>()

}