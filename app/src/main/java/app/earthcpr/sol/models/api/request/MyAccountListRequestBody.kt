package app.earthcpr.sol.models.api.request

import com.google.gson.annotations.SerializedName

data class MyAccountListRequestBody(
    @SerializedName("loginId")
    val loginId: String,
)