package project.paveltoy.movietap.data.loader

import project.paveltoy.movietap.BuildConfig
import project.paveltoy.movietap.data.entity.MovieGenres
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

const val GET_MOVIES = "/movie/{section}"
const val GET_MOVIES_BY_GENRE = "/discover/movie"
const val GET_GENRES = "/genre/movie/list"
const val API_KEY = "api_key"
const val LANGUAGE = "language"
const val REGION = "region"
const val PAGE = "page"
const val GENRE = "with_genres"

interface TMDBServerAPI {
    @GET(GET_MOVIES)
    fun getMoviesBySection(
        @Path("section") section: String,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(REGION) region: String,
        @Query(PAGE) page: Int,
    ): Call<LoadMovieResponse>

    @GET(GET_MOVIES_BY_GENRE)
    fun getMoviesByGenre(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(REGION) region: String,
        @Query(PAGE) page: Int,
        @Query(GENRE) genreId: Int,
    ): Call<LoadMovieResponse>

    @GET(GET_GENRES)
    fun getGenres(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
    ): Call<MovieGenres>
}