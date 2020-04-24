package id.candraibra.catmovie2.repository.model


import com.google.gson.annotations.SerializedName

data class AccountDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)