package com.example.searchvenues.model.template

/**
 * https://github.com/google/iosched/blob/84415833c606210f8cddc1458ea23c02a8401d3c/shared/src/main/java/com/google/samples/apps/iosched/shared/result/Result.kt
 *
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}