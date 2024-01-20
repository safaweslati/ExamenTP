package com.tp.examentp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp.examentp.Models.Movie
import com.tp.examentp.Models.MovieDetails

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()
    private val apiKey = "ea800ad24323df3e454f263fffdc2de4"

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> get() = _movieDetails

    init {
        loadMovies()
    }

    private fun loadMovies() {
        repository.getPopularMovies(apiKey, object : OnMoviesFetchedListener {
            override fun onMoviesFetched(movieList: List<Movie>) {
                _movies.postValue(movieList)
            }
            override fun onError(errorMessage: String) {
            }
        })
    }

    fun getMovieDetails(movieId: Int) {
        repository.getMovieDetails(apiKey, movieId, object : OnMovieDetailsFetchedListener {
            override fun onMovieDetailsFetched(movieDetails: MovieDetails) {
                _movieDetails.postValue(movieDetails)
            }
            override fun onError(errorMessage: String) {
            }
        })
    }
}




interface OnMoviesFetchedListener {
    fun onMoviesFetched(movieList: List<Movie>)
    fun onError(errorMessage: String)
}

interface OnMovieDetailsFetchedListener {
    fun onMovieDetailsFetched(movieDetails: MovieDetails)
    fun onError(errorMessage: String)
}

