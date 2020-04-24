package id.candraibra.catmovie2.repository.network

import id.candraibra.catmovie2.repository.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("account")
    suspend fun getAccountDetail(
        @Query("session_id") session: String
    ): AccountDetailResponse

    @GET("authentication/token/new")
    suspend fun getToken(): TokenRequest

    @POST("authentication/session/new")
    suspend fun postSession(
        @Body sessionRequest: SessionRequest
    ): SessionResponse

    @POST("authentication/token/validate_with_login")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest
    ): TokenRequest

}