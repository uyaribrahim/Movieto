package com.ri.movieto.presentation.mapper

import android.icu.text.SimpleDateFormat
import com.ri.movieto.domain.model.ReviewResponse
import com.ri.movieto.presentation.state.ReviewUIItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun ReviewResponse.Review.toReviewUIItem(): ReviewUIItem {

    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val dateTime = formatter.parse(created_at)
    val formatCreatedDate = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    val createdDate = formatCreatedDate.format(dateTime)


    var writtenBy = "Written by ${author_details.username}"
    if (writtenBy.length > 18) {
        writtenBy = writtenBy.take(18) + "..."
    }

    val createdAtText = "$writtenBy on $createdDate"
    val reviewTitle = "A review from ${author_details.username}"


    return ReviewUIItem(
        id = id,
        title = reviewTitle,
        rating = author_details.rating?.toString(),
        content = content,
        created_at = createdAtText,
        avatar_path = author_details.avatar_path
    )
}
