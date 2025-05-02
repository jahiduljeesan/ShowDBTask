package com.dev.jahid.showdbtask.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.jahid.showdbtask.R
import com.dev.jahid.showdbtask.databinding.ActivityDetailBinding
import com.dev.jahid.showdbtask.databinding.ActivityMainBinding
import com.dev.jahid.showdbtask.ui.adapter.MovieAdapter
import com.dev.jahid.showdbtask.ui.viewmodel.MovieViewmodel
import com.dev.jahid.showdbtask.utils.ApiConstance
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewmodel: MovieViewmodel;
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProvider(this)[MovieViewmodel::class.java]

        val id: Int = intent.getIntExtra("movie_id",-1)
        setData(id)

        binding.recyclerSimilarMovies.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        movieAdapter = MovieAdapter{
            setData(it.id)
        }
        binding.recyclerSimilarMovies.adapter = movieAdapter

        viewmodel.relatedList.observe(this) {
            movieAdapter.submitList(it)
        }

        binding.btnFavorite.setOnClickListener {
            viewmodel.addMovieToFavorites(ApiConstance.ACCOUNT_ID, ApiConstance.SESSION_ID, id)
            Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
        }


    }
    fun setData(id: Int) {
        if (id > 0) {
            viewmodel.getMovieDetails(id)
            viewmodel.movieDetails.observe(this){
                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500${it.poster_path}")
                    .resize(300, 300)
                    .centerCrop()
                    .into(binding.imgPoster)

                // Mistake that i awere about
                binding.tvTitle.text = it.title
                binding.tvLanguage.text = it.release_date
                binding.tvOverview.text = it.overview
                binding.tvReleaseDate.text = it.original_language

                viewmodel.getRelatedList(id)
            }
        }
    }
}