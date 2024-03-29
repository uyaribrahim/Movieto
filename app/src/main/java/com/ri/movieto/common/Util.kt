package com.ri.movieto.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ri.movieto.R


fun ImageView.downloadFromUrl(url: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.placeholder)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun ImageView.downloadCircleImage(url: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.placeholder)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .circleCrop()
        .into(this)
}

fun TextView.changeBgBySelectedCategory(isSelected: Boolean) {
    if (isSelected) {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.pink_panther))
    } else {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.midnight))
    }
}

fun ImageView.changeFavIconDrawable(isSelected: Boolean) {
    if (isSelected) {
        this.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_heart_fill
            )
        )
    } else {
        this.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_heart
            )
        )
    }
}