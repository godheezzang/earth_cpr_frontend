package app.earthcpr.sol.models

data class DepositAccount(
    val bankCode: String,
    val accountNo: String,
    val accountName: String,
    val accountBalance: String, // String으로 변경
)