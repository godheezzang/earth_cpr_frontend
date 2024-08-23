package app.earthcpr.sol.models.api.request

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("userEmail")
    val userEmail: String
)
