package com.ri.movieto.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
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

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?) {
    val posterPath = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2$url"
    view.downloadFromUrl(posterPath)
}

fun TextView.getYearFromDate(releaseDate: String) {
    val releaseDateArray = releaseDate.split("-")
    val releaseYear = releaseDateArray[0]
    this.text = releaseYear
}

@BindingAdapter("android:setTextFromDate")
fun getYear(view: TextView, releaseDate: String) {
    view.getYearFromDate(releaseDate)
}

fun TextView.changeBgBySelectedCategory(id: Int) {
    if (id == 0) {
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.pink_panther))
    }else{
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.midnight))
    }
}

@BindingAdapter("android:categoryId")
fun getCategoryId(view: TextView, id: Int) {
    view.changeBgBySelectedCategory(id)
}
