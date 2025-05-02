package com.dev.jahid.showdbtask.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dev.jahid.showdbtask.data.model.Movie
import com.dev.jahid.showdbtask.data.repository.MovieRepository
import com.dev.jahid.showdbtask.ui.view.FavoriteRequestBody
import com.dev.jahid.showdbtask.utils.ApiConstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewmodel : ViewModel() {

    private val repo = MovieRepository()
    val movieList = MutableLiveData<List<Movie>>()
    val movieDetails = MutableLiveData<Movie>()
    val relatedList = MutableLiveData<List<Movie>>()
    val favorites = MutableLiveData<List<Movie>>()

    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        Log.d("SizeOfArray", "I am here at popular movie method")
        viewModelScope.launch {
            try {
                val movies = repo.getPopularMovies().results ?: emptyList()
                Log.d("SizeOfArray", "Movies size ${movies.size}")
                movieList.value = movies
                Log.d("SizeOfArray", "I am here")
            } catch (e: Exception) {
               e.printStackTrace()
            }
        }
    }

    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                movieDetails.value = repo.getMovieDetails(id)
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getRelatedList(id:Int) {
        viewModelScope.launch {
            try {
                relatedList.value = repo.getRelatedList(id).results
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFavorites(id: Int) {
        viewModelScope.launch {
            try {
                favorites.value = repo.getFavorites(id).results?:emptyList()
                Log.d("FavoriteListCheck","I am in try")
            }catch (e: Exception) {
                e.printStackTrace()
                Log.d("FavoriteListCheck","I am in catch")
            }
        }
    }


    fun setFavoriteMovie(accountId: Int,favoriteRequestBody: FavoriteRequestBody) = liveData(Dispatchers.IO) {
       try {
           val favoriteResponse = repo.setFavorite(accountId,favoriteRequestBody)
           emit(favoriteResponse)
       }catch (e: Exception) {
           e.printStackTrace()
       }
    }

}
