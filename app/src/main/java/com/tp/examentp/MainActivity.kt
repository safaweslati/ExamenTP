package com.tp.examentp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp.examentp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , MovieAdapter.OnItemClickListener {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // Initialize adapter with an empty list
        movieAdapter = MovieAdapter(emptyList(),this)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this) { movies ->
            // Update the adapter with the new data
            movieAdapter.setMovies(movies)
            // Notify the adapter that the data has changed
            movieAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemClick(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }
}
