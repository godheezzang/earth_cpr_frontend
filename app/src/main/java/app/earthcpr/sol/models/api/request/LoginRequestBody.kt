package app.earthcpr.sol.models.api.request

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("loginId")
    val loginId: String,
    @SerializedName("password")
    val password: String
)
