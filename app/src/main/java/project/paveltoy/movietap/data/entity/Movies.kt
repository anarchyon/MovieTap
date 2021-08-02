package project.paveltoy.movietap.data.entity

class Movies {
    val sections: Sections = TMDBSections
    var genres: MovieGenres? = null
    var movieSectionsList: List<String> = listOf()
    var movieGenresList: List<String> = listOf()
    var movies = hashMapOf<String, List<MovieEntity>>()

}