package com.dev.jahid.showdbtask.data.repository

import com.dev.jahid.showdbtask.data.api.RetrofitInstance
import com.dev.jahid.showdbtask.data.model.FavoriteRequestBody
import com.dev.jahid.showdbtask.utils.ApiConstance

class MovieRepository {
    val api = RetrofitInstance.api
    //for commit

    suspend fun getPopularMovies() = api.getPopularMovies()
    suspend fun getMovieDetails(id: Int) = api.movieDetails(id)
    suspend fun getRelatedList(id:Int) = api.getRelatedList(id)

    suspend fun getFavorites(accountId:Int) = api.getFavorites(accountId)

    suspend fun setFavorite(accountId: Int,favoriteRequestBody: FavoriteRequestBody) = api.setMovieFavorite(ApiConstance.ACCOUNT_ID,
        favoriteRequestBody)
}