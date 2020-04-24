package id.candraibra.catmovie2.repository

import id.candraibra.catmovie2.repository.model.LoginRequest
import id.candraibra.catmovie2.repository.model.SessionRequest
import id.candraibra.catmovie2.repository.network.RemoteSource


class MyRepository(private val remoteSource: RemoteSource) {

    suspend fun getToken() = remoteSource.getToken()
    suspend fun postSession(sessionRequest: SessionRequest) = remoteSource.postSession(sessionRequest)
    suspend fun postLogin(loginRequest: LoginRequest) = remoteSource.postLogin(loginRequest)
    suspend fun getAccountDetail() = remoteSource.getAccountDetail()

}