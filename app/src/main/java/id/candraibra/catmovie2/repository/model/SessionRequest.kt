package id.candraibra.catmovie2.repository.model


import com.google.gson.annotations.SerializedName

data class SessionRequest(

    @SerializedName("request_token")
    val requestToken: String

)