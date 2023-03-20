package com.ri.movieto.presentation.state

data class ReviewUIItem(
    val id: String,
    val created_at: String,
    val content: String,
    val title: String,
    val avatar_path: String,
    val rating: String? = null,
)