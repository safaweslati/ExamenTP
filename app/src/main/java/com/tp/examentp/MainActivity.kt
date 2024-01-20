package com.tp.examentp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp.examentp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, MovieViewModelFactory(this)).get(MovieViewModel::class.java)

        // Initialize adapter with an empty list
        movieAdapter = MovieAdapter(emptyList(), this)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter

        observeViewModel()

        viewModel.connectionStatus.observe(this) { isConnected ->
            if (!isConnected) {
                showConnectionError()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this) { movies ->
            // Update the adapter with the new data
            movieAdapter.setMovies(movies)
            // Notify the adapter that the data has changed
            movieAdapter.notifyDataSetChanged()
        }
    }

    private fun showConnectionError() {
        val dialogView = layoutInflater.inflate(R.layout.no_internet, null)
        val retryButton = dialogView.findViewById<Button>(R.id.retryButton)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        retryButton.setOnClickListener {
            viewModel.loadMovies()
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onItemClick(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }
}
