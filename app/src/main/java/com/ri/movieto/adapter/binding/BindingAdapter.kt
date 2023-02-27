package com.ri.movieto.adapter.binding

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ri.movieto.common.Resource
import com.ri.movieto.util.changeBgBySelectedCategory
import com.ri.movieto.util.downloadFromUrl

@BindingAdapter("android:hideOnLoading")
fun ViewGroup.hideOnLoading(state: Resource<*>) {
    visibility = if (state is Resource.Loading)
        View.GONE
    else
        View.VISIBLE
}

@BindingAdapter("android:showOnLoading")
fun ProgressBar.showOnLoading(state: Resource<*>) {
    visibility = if (state is Resource.Loading)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("android:showOnError")
fun TextView.showError(state: Resource<*>) {
    if (state is Resource.Error) {
        visibility = View.VISIBLE
        text = state.message
    } else
        View.GONE
}

@BindingAdapter("android:hideOnError")
fun View.hideOnError(state: Resource<*>) {
    visibility = if (state is Resource.Error)
        View.GONE
    else
        View.VISIBLE
}

@BindingAdapter("android:backdropPath")
fun downloadBackdropImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url)
}

@BindingAdapter("android:posterPath")
fun downloadPosterImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url)
}

@BindingAdapter("android:categoryId")
fun getCategoryId(view: TextView, id: Int) {
    view.changeBgBySelectedCategory(id)
}
