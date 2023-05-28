package com.intermediate.substory

sealed class ResultCustom<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultCustom<T>()
    data class Error<out T>(val error: String) : ResultCustom<T>()
    object Loading : ResultCustom<Nothing>()
}