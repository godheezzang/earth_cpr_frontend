package app.earthcpr.sol.models.api.response

data class UserCoinResponse(
    val userUuid: String,
    val coinAmount: Float,
    val registerDate: String,
    val modifyDate: String
)