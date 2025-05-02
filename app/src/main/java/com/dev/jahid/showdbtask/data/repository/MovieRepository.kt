package com.dev.jahid.showdbtask.data.repository

import com.dev.jahid.showdbtask.data.api.RetrofitInstance
import com.dev.jahid.showdbtask.data.model.Movie
import com.dev.jahid.showdbtask.ui.view.FavoriteRequestBody
import com.dev.jahid.showdbtask.utils.ApiConstance
import retrofit2.Response

class MovieRepository {
    val api = RetrofitInstance.api

    suspend fun getPopularMovies() = api.getPopularMovies()
    suspend fun getMovieDetails(id: Int) = api.movieDetails(id)
    suspend fun getRelatedList(id:Int) = api.getRelatedList(id)

    suspend fun addFavorite(accountId: String, sessionId: String, movieId: Int): Boolean {
        val body = FavoriteRequestBody(media_id = movieId, favorite = true)
        val response = api.markAsFavorite(accountId, sessionId, body)
        return response.isSuccessful
    }
    suspend fun getFavorites(accountId: String, sessionId: String): List<Movie>? {
        val response = api.getFavoriteMovies(accountId, sessionId)
        return if (response.isSuccessful) response.body()?.results else null
    }
}