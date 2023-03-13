package com.ri.movieto.error

import com.ri.movieto.common.Resource
import retrofit2.HttpException
import java.io.IOException

class ErrorHandler {

    fun getErrorMessage(error: Exception): String {
        return when (error) {
            is HttpException -> error.localizedMessage ?: "Beklenmeyen bir hata oluştu"
            is IOException -> "Lütfen internet bağlantınızı kontrol edin"
            else -> "Beklenmeyen bir hata oluştu. " + error.message.toString()
        }
    }
}