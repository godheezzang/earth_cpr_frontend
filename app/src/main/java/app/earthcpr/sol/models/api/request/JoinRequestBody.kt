package app.earthcpr.sol.models.api.request

import com.google.gson.annotations.SerializedName

data class JoinRequestBody(
    @SerializedName("loginId")
    val loginId: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("userNickname")
    val userNickname: String
)
