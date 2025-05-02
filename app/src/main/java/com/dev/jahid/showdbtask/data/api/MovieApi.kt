package com.dev.jahid.showdbtask.data.api

import com.dev.jahid.showdbtask.data.model.Movie
import com.dev.jahid.showdbtask.data.model.MovieResponse
import com.dev.jahid.showdbtask.ui.view.FavoriteRequestBody
import com.dev.jahid.showdbtask.ui.view.FavoriteResponse
import com.dev.jahid.showdbtask.utils.ApiConstance
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") auth: String = "Bearer ${ApiConstance.ACCESS_TOKEN}"
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun movieDetails(
        @Path("movie_id") id:Int,
        @Header("Authorization") auth: String =  "Bearer ${ApiConstance.ACCESS_TOKEN}"
    ): Movie

    @GET("movie/{movie_id}/similar")
    suspend fun getRelatedList(
        @Path("movie_id") id: Int,
        @Header("Authorization") auth: String = "Bearer ${ApiConstance.ACCESS_TOKEN}"
    ): MovieResponse

    @POST("account/{account_id}/favorite")
    suspend fun setMovieFavorite(
        @Path("account_id") accountId: Int,
        @Body body: FavoriteRequestBody,
        @Header("Authorization") auth: String = "Bearer ${ApiConstance.ACCESS_TOKEN}",
    ): FavoriteResponse

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorites(
        @Path("account_id") accountId: Int,
        @Header("Authentication") auth: String = "Bearer ${ApiConstance.ACCESS_TOKEN}"
    ): MovieResponse

}