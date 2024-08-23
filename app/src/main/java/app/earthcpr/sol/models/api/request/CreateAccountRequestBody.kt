package app.earthcpr.sol.models.api.request

import com.google.gson.annotations.SerializedName

data class CreateAccountRequestBody(
    @SerializedName("withdrawalAccountNo")
    val withdrawalAccountNo: String, // 출금 계좌번호
    @SerializedName("accountTypeUniqueNo")
    val accountTypeUniqueNo: String, // 상품 고유번호
    @SerializedName("depositBalance")
    val depositBalance: String // 가입 금액
)
