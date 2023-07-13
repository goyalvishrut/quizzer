package com.example.quizzer.model

sealed class Response<out T : Any?> {
    data class Success<T : Any>(val body: T) : Response<T>()

    data class Error(val error: RestApiError) : Response<Nothing>()
}
