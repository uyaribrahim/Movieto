package com.ri.movieto.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ri.movieto.R
import kotlin.math.roundToInt


fun ImageView.downloadFromUrl(url: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.placeholder)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

@BindingAdapter("android:backdropPath")
fun downloadBackdropImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url)
}

@BindingAdapter("android:posterPath")
fun downloadPosterImage(view: ImageView, url: String?) {
    val posterPath = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2$url"
    view.downloadFromUrl(posterPath)
}

fun TextView.changeBgBySelectedCategory(id: Int) {
    if (id == 0) {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.pink_panther))
    } else {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.midnight))
    }
}

@BindingAdapter("android:categoryId")
fun getCategoryId(view: TextView, id: Int) {
    view.changeBgBySelectedCategory(id)
}

fun TextView.roundVoteAverage(vote_average: Float) {
    val roundOff = (vote_average * 10.0).roundToInt() / 10.0
    this.text = roundOff.toString()
}

@BindingAdapter("android:voteAverage")
fun getVoteAverage(view: TextView, vote_average: Float) {
    view.roundVoteAverage(vote_average)
}
