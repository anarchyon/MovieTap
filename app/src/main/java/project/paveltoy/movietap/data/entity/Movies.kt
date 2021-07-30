package project.paveltoy.movietap.data.entity

class Movies {
    val sections: Sections = TMDBSections
    var genres: MovieGenres? = null
    var movieSectionsList: List<String>? = null
    var movieGenresList: List<String>? = null
    var movies = hashMapOf<String, List<MovieEntity>>()

}