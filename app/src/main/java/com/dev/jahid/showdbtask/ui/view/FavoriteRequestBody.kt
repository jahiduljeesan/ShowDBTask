package com.dev.jahid.showdbtask.ui.view

data class FavoriteRequestBody(
    val media_type: String = "movie",
    val media_id: Int,
    val favorite: Boolean
)
