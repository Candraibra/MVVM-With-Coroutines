package id.candraibra.catmovie2.utils

import id.candraibra.catmovie2.utils.Status.*


data class Result<out T>(val status: Status, val data: T?, val message: String?, val code: Int?) {
    companion object {
        fun <T> success(data: T): Result<T> =
            Result(status = SUCCESS, data = data, message = null, code = null)

        fun <T> error(data: T?, message: String, code: Int): Result<T> =
            Result(status = ERROR, data = data, message = message, code = code)

        fun <T> loading(data: T?): Result<T> =
            Result(status = LOADING, data = data, message = null, code = null)
    }
}