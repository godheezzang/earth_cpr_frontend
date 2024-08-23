package app.earthcpr.sol.models

data class AccountDetail(
    val accountName: String,  // 상품명
    val accountDescription: String, // 상품설명
    val accountNo: String, // 계좌 번호
    val bankName: String, // 은행명
    val accountCreateDate: String,  // 계좌 개설일
    val accountExpiryDate: String,  // 계좌 만기일
    val withdrawalBankName: String, // 출금은행명
    val withdrawalAccountNo: String, // 출금 계좌번호
    val interestNumber: String, // 회차
    val interestRate: Double, // 가입 적용 금리
    val totalBalance: Long, // 누적 납입 금액
) {
    constructor() : this("", "", "", "", "", "", "", "", "", 0.0, 0L)


}

