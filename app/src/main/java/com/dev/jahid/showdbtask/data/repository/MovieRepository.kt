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

    suspend fun getFavorites(id:Int) = api.getFavorites(id)

    suspend fun setFavorite(accountId: Int,favoriteRequestBody: FavoriteRequestBody) = api.setMovieFavorite(ApiConstance.ACCOUNT_ID,
        favoriteRequestBody)
}