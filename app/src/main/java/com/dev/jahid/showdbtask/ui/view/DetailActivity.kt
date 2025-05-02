package com.dev.jahid.showdbtask.ui.view

import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProvider(this)[MovieViewmodel::class.java]

        //button arrow back
        binding.btnArrowBack.setOnClickListener {
            finish()
        }

        var id: Int = intent.getIntExtra("movie_id",-1)
        setData(id) // setting data to activity views

        //setting data to recycler view list.....................
        binding.recyclerSimilarMovies.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        movieAdapter = MovieAdapter{
            setData(it.id)
            id = it.id
        }
        binding.recyclerSimilarMovies.adapter = movieAdapter

        viewmodel.relatedList.observe(this) {
            movieAdapter.submitList(it)
        }
        //..........................................


        viewmodel.favorites.observe(this) {
            val isFavorite = it.any {
                id == it.id
            }

            if (isFavorite) {
                binding.btnFavorite.text = "🖤 Favorite"
            }else {
                binding.btnFavorite.text = "Add to Favorite"
                binding.btnFavorite.setOnClickListener {
                    viewmodel.setFavoriteMovie(ApiConstance.ACCOUNT_ID,
                        FavoriteRequestBody("movie", id, true)
                    )
                        .observe(this) {
                            Toast.makeText(this, "${it.status_message}", Toast.LENGTH_SHORT).show()
                            if (it.status_message == "Success.") binding.btnFavorite.text = "🖤 Favorite"
                        }
                }
            }
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