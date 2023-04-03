package com.ri.movieto.error

import android.util.Log
import retrofit2.HttpException
import java.io.IOException

class ErrorHandler {

    fun getErrorMessage(error: Exception): String {
        Log.e("ERROR",error.message.toString())
        return when (error) {
            is HttpException -> error.localizedMessage ?: "Beklenmeyen bir hata oluştu"
            is IOException -> "Lütfen internet bağlantınızı kontrol edin"
            else -> "Beklenmeyen bir hata oluştu. " + error.message.toString()
        }
    }
}