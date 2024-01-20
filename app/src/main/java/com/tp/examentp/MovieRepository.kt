package com.tp.examentp

import android.util.Log
import com.tp.examentp.Models.Movie
import com.tp.examentp.Models.MovieDetails
import com.tp.examentp.Models.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {
    private val movieApiService: MovieApiService = Retrofit.createApiService()

    fun getPopularMovies(apiKey: String, listener: OnMoviesFetchedListener) {
        val call = movieApiService.getPopularMovies(apiKey)

        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("MovieViewModel", "Movies fetched successfully")
                        listener.onMoviesFetched(it.results)
                    }
                } else {
                    Log.e("MovieRepository", "Error response: ${response.code()}")
                    listener.onError("Error fetching movies")
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                listener.onError("Connection error")
            }
        })
    }

    fun getMovieDetails(apiKey: String, movieId: Int, listener: OnMovieDetailsFetchedListener) {
        val call = movieApiService.getMovieDetails(movieId, apiKey)

        call.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onMovieDetailsFetched(it)
                    }
                } else {
                    listener.onError("Error fetching movie details")
                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                listener.onError("Connection error")
            }
        })
    }

}
