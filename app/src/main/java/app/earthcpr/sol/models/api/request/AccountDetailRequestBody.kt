package app.earthcpr.sol.models.api.request

import com.google.gson.annotations.SerializedName

data class AccountDetailRequestBody(
    @SerializedName("accountNo")
    val accountNo: String, // 계좌번호
)
