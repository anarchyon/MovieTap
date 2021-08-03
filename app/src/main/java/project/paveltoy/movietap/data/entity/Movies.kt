package project.paveltoy.movietap.data.entity

class Movies {
    val sections: Sections = TMDBSections
    var movieGenres: MovieGenres? = null
    var movieSectionsList: List<String> = listOf()
    var movieGenresList: List<String> = listOf()
    var movieSet = hashMapOf<String, List<MovieEntity>>()

}