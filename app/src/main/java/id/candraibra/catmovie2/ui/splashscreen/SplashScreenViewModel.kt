package id.candraibra.catmovie2.ui.splashscreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.candraibra.catmovie2.BuildConfig.PASSWORD
import id.candraibra.catmovie2.BuildConfig.USERNAME
import id.candraibra.catmovie2.repository.MyRepository
import id.candraibra.catmovie2.repository.model.LoginRequest
import id.candraibra.catmovie2.repository.model.SessionRequest
import id.candraibra.catmovie2.utils.ErrorBody.Companion.getErrorMessage
import id.candraibra.catmovie2.utils.PrefHelper
import id.candraibra.catmovie2.utils.PrefKey
import id.candraibra.catmovie2.utils.Result
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException


class SplashScreenViewModel(private val mainRepository: MyRepository) : ViewModel() {

    fun getToken() = liveData(Dispatchers.IO) {
        emit(Result.loading(data = null))
        try {
            emit(Result.success(data = mainRepository.getToken()))
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val body = throwable.response()?.errorBody()
                    emit(
                        Result.error(data = null, message = getErrorMessage(body), code = code)
                    )
                }
                is IOException -> {
                    emit(Result.error(data = null, message = "Network Error", code = 0))
                }
                else -> {
                    emit(Result.error(data = null, message = "Error Occurred!", code = 0))
                }
            }
        }
    }

    fun postSession() = liveData(Dispatchers.IO) {
        emit(Result.loading(data = null))
        try {
            emit(Result.success(data = mainRepository.postSession(sessionRequest())))
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val body = throwable.response()?.errorBody()
                    emit(Result.error(data = null, message = getErrorMessage(body), code = code))
                }
                is IOException -> {
                    emit(Result.error(data = null, message = "Network Error", code = 0))
                }
                else -> {
                    emit(Result.error(data = null, message = "Error Occurred!", code = 0))
                }
            }
        }
    }

    fun postLogin() = liveData(Dispatchers.Default) {
        emit(Result.loading(data = null))
        try {
            emit(Result.success(data = mainRepository.postLogin(loginRequest())))
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val body = throwable.response()?.errorBody()
                    emit(Result.error(data = null, message = getErrorMessage(body), code = code))
                }
                is IOException -> {
                    emit(Result.error(data = null, message = "Network Error", code = 0))
                }
                else -> {
                    emit(Result.error(data = null, message = "Error Occurred!", code = 0))
                }
            }
        }
    }

    private fun sessionRequest(): SessionRequest {
        val token = PrefHelper().getString(PrefKey.TOKEN)!!
        return SessionRequest(token)
    }

    private fun loginRequest(): LoginRequest {
        val token = PrefHelper().getString(PrefKey.TOKEN)!!
        return LoginRequest(PASSWORD, token, USERNAME)
    }
}