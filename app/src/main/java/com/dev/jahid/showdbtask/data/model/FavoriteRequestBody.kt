package com.dev.jahid.showdbtask.data.model

data class FavoriteRequestBody(
    val media_type: String = "movie",
    val media_id: Int,
    val favorite: Boolean
)
