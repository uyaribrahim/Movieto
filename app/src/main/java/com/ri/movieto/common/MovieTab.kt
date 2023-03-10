package com.ri.movieto.common

import androidx.fragment.app.Fragment
import com.ri.movieto.presentation.movie.tabs.CastFragment
import com.ri.movieto.presentation.movie.tabs.CommentsFragment
import com.ri.movieto.presentation.movie.tabs.RecommendFragment

enum class MovieTab(val fragment: Fragment, val title: String) {

    Cast(CastFragment(), "Cast"),
    Recommend(RecommendFragment(), "Similar"),
    Comments(CommentsFragment(), "Comments")

}