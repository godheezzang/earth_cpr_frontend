package app.earthcpr.sol.screens.savings.mydepositaccountlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import app.earthcpr.sol.models.DepositAccount
import app.earthcpr.sol.models.MyAccount

object MockDepositAccountListApiData {
    private val _selectedAccountNo = mutableStateOf("")
    val selectedAccountNo: State<String> = _selectedAccountNo

    fun onClickItem(accountNo: String) {
        _selectedAccountNo.value = accountNo
    }
//    val AccountList = listOf(
//        MyAccount(
//            "ESG 실천 챌린지형 적금",
//            "2024.08.18",
//            "2025.08.17",
//            "1234-123-12345",
//            "1회차",
//            "2.6",
//            "500000L"
//        ) { onClickItem(accountNo = "1234-123-12345") },
//        MyAccount(
//            "웰니스 챌린지형 적금",
//            "2024.08.18",
//            "2025.08.17",
//            "1234-123-12345",
//            "1회차",
//            "2.6",
//            "1000000L"
//        ) { onClickItem(accountNo = "1234-123-12345") },
}