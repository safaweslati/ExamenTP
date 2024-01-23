package com.tp.examentp

import MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tp.examentp.Models.Movie
import com.tp.examentp.Models.MovieDetails
import android.content.Context
import android.widget.Toast


class MovieViewModel(private val context: Context) : ViewModel() {
    private val repository = MovieRepository(context)
    private val apiKey = "ea800ad24323df3e454f263fffdc2de4"
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> get() = _movieDetails

    private val _connectionStatus = MutableLiveData<Boolean>()
    val connectionStatus: LiveData<Boolean> get() = _connectionStatus

    init {
        loadMovies()
    }
    fun loadMovies() {
        if (repository.isNetworkAvailable()) {
            repository.getPopularMovies(apiKey, object : OnMoviesFetchedListener {
                override fun onMoviesFetched(movieList: List<Movie>) {
                    _movies.postValue(movieList)
                }

                override fun onError(errorMessage: String) {
                    Toast.makeText(context, "Error fetching movies", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else{
            _connectionStatus.postValue(false)
        }
    }
    fun getMovieDetails(movieId: Int) {
        if (repository.isNetworkAvailable()) {
            repository.getMovieDetails(apiKey, movieId, object : OnMovieDetailsFetchedListener {
            override fun onMovieDetailsFetched(movieDetails: MovieDetails) {
                _movieDetails.postValue(movieDetails)
            }
            override fun onError(errorMessage: String) {
                Toast.makeText(context, "Error fetching movie details", Toast.LENGTH_SHORT).show()
            }
        })
        }
        else{
            _connectionStatus.postValue(false)
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()

        }
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

