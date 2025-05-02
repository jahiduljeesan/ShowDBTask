package com.dev.jahid.showdbtask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.jahid.showdbtask.R
import com.dev.jahid.showdbtask.data.model.Movie
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class MovieAdapter(private val onClick:(Movie) -> Unit):
    ListAdapter<Movie, MovieAdapter.MovieVH> (DiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie,parent,false)
        return MovieVH(view)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        setData(holder,position)

        holder.movieItem.setOnClickListener {
            onClick(getItem(position))
        }

    }

    private fun setData(holder: MovieVH, position: Int) {
        val movie = getItem(position)

        holder.tvTitle.text = movie.title
        holder.tvDate.text = movie.release_date
        holder.tvLanguage.text = movie.original_language
        holder.tvOverview.text = movie.overview

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .resize(100, 100)
            .centerCrop()
            .into(holder.ivPoster)


    }


    class MovieVH(view: View): RecyclerView.ViewHolder(view) {
        val movieItem: ConstraintLayout = view.findViewById(R.id.movieItem)

        val ivPoster: ShapeableImageView = view.findViewById(R.id.ivPoster)

        val tvLanguage: TextView = view.findViewById(R.id.tvLanguage)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvOverview: TextView = view.findViewById(R.id.tvOverview)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    }


    class DiffCallBack: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem ==newItem

    }

}