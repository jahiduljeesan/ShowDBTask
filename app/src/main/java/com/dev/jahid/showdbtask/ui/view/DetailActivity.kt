package com.dev.jahid.showdbtask.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.jahid.showdbtask.data.model.FavoriteRequestBody
import com.dev.jahid.showdbtask.databinding.ActivityDetailBinding
import com.dev.jahid.showdbtask.ui.adapter.MovieAdapter
import com.dev.jahid.showdbtask.ui.viewmodel.MovieViewmodel
import com.dev.jahid.showdbtask.utils.ApiConstance
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewmodel: MovieViewmodel;
    private lateinit var movieAdapter: MovieAdapter
    private var isFab = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProvider(this)[MovieViewmodel::class.java]

        //button arrow back
        binding.btnArrowBack.setOnClickListener {
            finish()
        }

        var id: Int = intent.getIntExtra("movie_id", -1)
        setData(id) // setting data to activity views

        //setting data to recycler view list.....................
        binding.recyclerSimilarMovies.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        movieAdapter = MovieAdapter {
            setData(it.id)
            id = it.id
        }
        binding.recyclerSimilarMovies.adapter = movieAdapter

        viewmodel.relatedList.observe(this) {
            movieAdapter.submitList(it)
        }
        //..........................................

        viewmodel.favoriteList.observe(this) {
            isFab = it.any {
                it.id == id }
            Log.d("Checkxyz","$isFab is fab")
            if (isFab) {
                binding.btnFavorite.text = "Remove from Favorites"
            } else {
                binding.btnFavorite.text = "Add to Favorites"
            }

            binding.btnFavorite.setOnClickListener {
                // Send the opposite of current state
                setFavoriteMovie(isFab, id).observe(this) {
                    Toast.makeText(this, it.status_message, Toast.LENGTH_SHORT).show()
                    isFab = !isFab
                    binding.btnFavorite.text = if (isFab) {
                        "Remove from Favorites"
                    } else {
                        "Add to Favorites"
                    }

                    viewmodel.getFavorites(ApiConstance.ACCOUNT_ID)
                }
            }
        }



    }


    private fun setFavoriteMovie(isFavorite: Boolean, id: Int)=
        viewmodel.setFavoriteMovie(ApiConstance.ACCOUNT_ID, FavoriteRequestBody(
            "movie",id,!isFavorite
        ))


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