package id.candraibra.catmovie2.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.candraibra.catmovie2.repository.MyRepository
import id.candraibra.catmovie2.utils.ErrorBody.Companion.getErrorMessage
import id.candraibra.catmovie2.utils.Result
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException

class AccountViewModel(private val mainRepository: MyRepository) : ViewModel() {

    fun getAccountDetail() = liveData(Dispatchers.IO) {
        emit(Result.loading(data = null))
        try {
            emit(Result.success(data = mainRepository.getAccountDetail()))
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
}