package com.ri.movieto.domain.decider

import com.ri.movieto.common.Constants

class MovieDecider {
    fun provideBackdropPath(path: String?): String = "${Constants.BACKDROP_PATH}$path"

    fun provideProfilePath(path: String?): String = "${Constants.PROFILE_PATH}$path"

    fun provideAvatarPath(path: String): String {
        if (path.isEmpty()) {
            return "https://www.gravatar.com/avatar/47050d66157a1b562e149d585315e5de.jpg?s=150"
        }
        return if (path.contains("gravatar")) {
            path.drop(1)
        } else {
            Constants.AVATAR_PATH + path
        }

    }
}