package com.ri.movieto.adapter.binding

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ri.movieto.common.Resource
import com.ri.movieto.util.*

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

@BindingAdapter("android:image")
fun downloadImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url)
}

@BindingAdapter("android:circleImage")
fun downloadAvatarImage(view: ImageView, url: String?) {
    view.downloadCircleImage(url)
}

@BindingAdapter("android:isSelectedCategory")
fun getCategoryId(view: TextView, isSelected: Boolean) {
    view.changeBgBySelectedCategory(isSelected)
}

@BindingAdapter("android:isVisible")
fun View.isVisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else View.GONE
}

@BindingAdapter("android:hideIfNull")
fun View.hideIfNull(value: String?) {
    visibility = if (value == null) {
        View.GONE
    } else View.VISIBLE
}

@BindingAdapter("android:isFavMovie")
fun getFavMovie(view: ImageView, isSelected: Boolean) {
    view.changeFavIconDrawable(isSelected)
}