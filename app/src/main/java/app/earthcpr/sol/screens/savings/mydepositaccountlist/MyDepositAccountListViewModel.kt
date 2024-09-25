package app.earthcpr.sol.screens.savings.mydepositaccountlist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.earthcpr.sol.MainActivity
import app.earthcpr.sol.MainActivity.Companion.loginId
import app.earthcpr.sol.models.DepositAccount
import app.earthcpr.sol.models.api.request.CreateAccountRequestBody
import app.earthcpr.sol.models.api.request.MyAccountListRequestBody
import app.earthcpr.sol.services.apiService
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyDepositAccountListViewModel @Inject constructor() : ViewModel() {
    private val _myDepositAccountList = mutableStateOf(MyDepositAccountListModel())
    val myDepositAccountList: State<MyDepositAccountListModel> = _myDepositAccountList
    private val TAG: String = "SavingAccountListViewModel"

    init {
        getMyDepositAccountList()
    }

    // 나의 적금 계좌 목록 조회 =
    private fun getMyDepositAccountList(
//        loginId: String
    ) {
        viewModelScope.launch {
            try {
                // todo
//                val requestBody = CreateAccountRequestBody(email)
//                val userUuid = apiService.postSavingAccount(requestBody).result
//                MainActivity.preferences.setString("userUuid", userUuid ?: "")
//                MainActivity.initUserUuidIfNull()
                _myDepositAccountList.value = MyDepositAccountListModel(
                    depositAccountList = MockDepositAccountListApiData.depositAccountList,
                    hasError = false
                )
            } catch (e: Exception) {
                Log.e(TAG, "[사용자별 입출금 계좌 조회] API ERROR OCCURED")
            }
        }
    }
}

data class MyDepositAccountListModel(
    val depositAccountList: List<DepositAccount> = emptyList(),
    val hasError: Boolean = false
)


//data class MyDepositAccountListModel(
//    val depositAccountList: List<DepositAccount> = emptyList(),
//    val hasError: Boolean = false
//)
//
//data class DepositAccountListResponse(
//    val success: Boolean,
//    val data: List<DepositAccount>
//)