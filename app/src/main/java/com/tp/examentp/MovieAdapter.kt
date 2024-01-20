package com.tp.examentp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tp.examentp.Models.Movie
import com.tp.examentp.databinding.ItemMovieBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale




class MovieAdapter(private var movies: List<Movie>?,public val listener: OnItemClickListener?) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movieId: Int)
    }

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val posterImage = binding.posterImage
        val date = binding.releaseDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (movies != null)
            return movies!!.size
        else return 0
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieItem = movies?.get(position)
        holder.title.text = movieItem?.title
        val date = formatDate(movieItem?.release_date)
        holder.date.text = "Release Date: ${date.toString()}"
        val imageUrl = "https://image.tmdb.org/t/p/w500/${movieItem?.poster_path}"
        Glide.with(holder.posterImage.context)
            .load(imageUrl)
            .into(holder.posterImage)

        holder.itemView.setOnClickListener {
            val movieId = movies?.get(position)?.id ?: -1
            listener?.onItemClick(movieId)
        }
    }

    private fun formatDate(inputDate: String?): String {
        if (inputDate.isNullOrEmpty()) {
            return ""
        }
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return inputDate
    }



    fun setMovies(movies: List<Movie>?) {
        this.movies = movies
    }
}

