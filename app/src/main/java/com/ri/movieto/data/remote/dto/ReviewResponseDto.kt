package com.ri.movieto.data.remote.dto

import com.ri.movieto.common.Constants
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.ReviewResponse

data class ReviewResponseDto(
    val id: Int,
    val page: Int,
    val results: List<ReviewDto>,
    val total_pages: Int,
    val total_results: Int
) {
    data class ReviewDto(
        val author: String,
        val author_details: AuthorDetailsDto,
        val content: String,
        val created_at: String,
        val id: String,
        val updated_at: String,
        val url: String
    ) {
        data class AuthorDetailsDto(
            val avatar_path: String? = null,
            val name: String,
            val rating: Int? = null,
            val username: String
        )
    }
}

fun ReviewResponseDto.toDomain(decider: MovieDecider): ReviewResponse {
    return ReviewResponse(
        id = id,
        results = results.map { review ->
            ReviewResponse.Review(
                author = review.author,
                id = review.id,
                author_details = ReviewResponse.Review.AuthorDetails(
                    decider.provideAvatarPath(review.author_details.avatar_path),
                    review.author_details.name,
                    review.author_details.rating,
                    review.author_details.username
                ),
                content = review.content,
                created_at = review.created_at
            )
        }
    )
}