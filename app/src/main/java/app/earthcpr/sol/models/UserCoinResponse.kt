package app.earthcpr.sol.models

data class UserCoinResponse(
    val userUuid: String,
    val coinAmount: Float,
    val registerDate: String,
    val modifyDate: String
)