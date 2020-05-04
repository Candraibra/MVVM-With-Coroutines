package id.candraibra.catmovie2.ui.splashscreen


import androidx.lifecycle.*
import id.candraibra.catmovie2.BuildConfig.PASSWORD
import id.candraibra.catmovie2.BuildConfig.USERNAME
import id.candraibra.catmovie2.repository.MyRepository
import id.candraibra.catmovie2.repository.model.LoginRequest
import id.candraibra.catmovie2.repository.model.SessionRequest
import id.candraibra.catmovie2.repository.model.SessionResponse
import id.candraibra.catmovie2.utils.ErrorBody.Companion.getErrorMessage
import id.candraibra.catmovie2.utils.PrefHelper
import id.candraibra.catmovie2.utils.PrefKey
import id.candraibra.catmovie2.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class SplashScreenViewModel(private val mainRepository: MyRepository) : ViewModel() {

    fun getToken() = liveData(Dispatchers.IO) {
        emit(Result.loading())
        try {
            emit(Result.success(mainRepository.getToken()))
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val body = throwable.response()?.errorBody()

                    emit(
                        Result.error(getErrorMessage(body), code)
                    )
                }
                is IOException -> {
                    emit(Result.error("Network Error", 0))
                }
                else -> {
                    emit(Result.error("Error Occurred!", 0))
                }
            }
        }
    }

    private val session = MutableLiveData<Result<SessionResponse>>()

    fun postValidateAndSession() = viewModelScope.launch(Dispatchers.IO) {
        session.postValue(Result.loading())
        try {
            mainRepository.postLogin(loginRequest())
            session.postValue(Result.success(mainRepository.postSession(sessionRequest())))
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val body = throwable.response()?.errorBody()
                    session.postValue(Result.error(getErrorMessage(body), code))
                }
                is IOException -> {
                    session.postValue(Result.error("Network Error",null))
                }
                else -> {
                    session.postValue(Result.error("something went wrong", null))
                }
            }
        }
    }

    fun getSession(): LiveData<Result<SessionResponse>> {
        return session
    }

    private fun sessionRequest(): SessionRequest {
        val token = PrefHelper.getString(PrefKey.TOKEN)!!
        return SessionRequest(token)
    }

    private fun loginRequest(): LoginRequest {
        val token = PrefHelper.getString(PrefKey.TOKEN)!!
        return LoginRequest(PASSWORD, token, USERNAME)
    }
}