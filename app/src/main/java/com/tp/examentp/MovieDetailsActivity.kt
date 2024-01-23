package com.tp.examentp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tp.examentp.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieViewModel
    var movieId: Int = 0


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getIntExtra("movieId",0)
        viewModel =
            ViewModelProvider(this, MovieViewModelFactory(this)).get(MovieViewModel::class.java)

        viewModel.getMovieDetails(movieId)

        viewModel.movieDetails.observe(this) { movieDetails ->
            val imageUrl = "https://image.tmdb.org/t/p/w500/${movieDetails?.poster_path}"
            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .into(binding.imageView)
            binding.title.text = movieDetails.title
            binding.date.text = movieDetails.release_date
            binding.overview.text = movieDetails.overview
            binding.time.text = " ${formatRuntime(movieDetails.runtime)}"
            val genres = movieDetails.genres
            val genreNames = genres.joinToString(", ") { it.name }
            binding.genres.text = genreNames
            val userRatingPercentage =
                (movieDetails?.vote_average?.div(10.0)?.times(100))?.toInt()
            if (userRatingPercentage != null) {
                binding.progressBar.progress = userRatingPercentage
                binding.textViewProgress.text = "$userRatingPercentage%"
            }
        }

        viewModel.connectionStatus.observe(this, Observer { isConnected ->
            if (!isConnected) {
                showConnectionError()
            }
        })

    }

    private fun showConnectionError() {
        val dialogView = layoutInflater.inflate(R.layout.no_internet, null)
        val retryButton = dialogView.findViewById<Button>(R.id.retryButton)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
        retryButton.setOnClickListener {
            viewModel.getMovieDetails(movieId)
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun formatRuntime(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        return if (hours > 0) {
            String.format("%dh%02d", hours, remainingMinutes)
        } else {
            String.format("%d min", remainingMinutes)
        }
    }


}