package app.earthcpr.sol.models

data class MyAccount(
    val accountName: String,  // 상품명
    val accountCreateDate: String,  // 계좌 개설일
    val accountExpiryDate: String,  // 계좌 만기일
    val accountNo: String, // 계좌 번호
    val interestNumber: String, // 회차
    val interestRate: Double, // 가입 적용 금리
    val totalBalance: Long, // 누적 납입 금액
    val onClick: () -> Unit,
)