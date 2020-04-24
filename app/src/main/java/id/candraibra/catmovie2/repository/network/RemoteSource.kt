package id.candraibra.catmovie2.repository.network

import id.candraibra.catmovie2.repository.model.LoginRequest
import id.candraibra.catmovie2.repository.model.SessionRequest
import id.candraibra.catmovie2.utils.PrefHelper
import id.candraibra.catmovie2.utils.PrefKey

class RemoteSource(private val apiService: ApiService) {
    private val session = PrefHelper().getString(PrefKey.SESSION)!!

    suspend fun getToken() = apiService.getToken()
    suspend fun postSession(sessionRequest: SessionRequest) = apiService.postSession(sessionRequest)
    suspend fun postLogin(loginRequest: LoginRequest) = apiService.postLogin(loginRequest)
    suspend fun getAccountDetail() = apiService.getAccountDetail(session)


}