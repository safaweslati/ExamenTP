package com.tp.examentp

import com.tp.examentp.Models.Movie
import com.tp.examentp.Models.MovieDetails
import com.tp.examentp.Models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Path


interface MovieApiService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<MovieDetails>
}