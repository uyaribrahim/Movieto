package com.ri.movieto.domain.model

data class ReviewResponse(
    val id: Int,
    val results: List<Review>,
) {
    data class Review(
        val author: String,
        val author_details: AuthorDetails,
        val content: String,
        val created_at: String,
        val id: String,
    ) {
        data class AuthorDetails(
            val avatar_path: String,
            val name: String,
            val rating: Int? = null,
            val username: String
        )
    }
}
