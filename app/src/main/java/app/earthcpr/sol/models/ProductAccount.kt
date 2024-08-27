package app.earthcpr.sol.models

// 적금 상품
data class ProductAccount(
    val increaseInterestRate: String,
    val challengeList: List<Challenge>?,
    val accountName: String,
    val bankCode: String,
    val accountDescription: String,
    val subscriptionPeriod: String,
    val minSubscriptionBalance: Int,
    val maxSubscriptionBalance: Int,
    val interestRate: String,
    val rateDescription: String,
)

data class Challenge(
    val id: Int,
    val name: String,
    val info: String,
    val type: Int,
    val verification: Int
)