package id.candraibra.catmovie2.repository.model


import com.google.gson.annotations.SerializedName

data class TokenRequest(

    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("request_token")
    val requestToken: String,
    @SerializedName("success")
    val success: Boolean
)