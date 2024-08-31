package app.earthcpr.sol.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.earthcpr.sol.MainActivity
import app.earthcpr.sol.MainActivity.Companion.loginId
import app.earthcpr.sol.models.DepositAccount
import app.earthcpr.sol.models.MyAccount
import app.earthcpr.sol.models.api.request.CreateAccountRequestBody
import app.earthcpr.sol.models.api.request.MyAccountListRequestBody
import app.earthcpr.sol.screens.savings.myaccountlist.MyAccountListModel
import app.earthcpr.sol.services.apiService
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {
    private val _homeModel = mutableStateOf(HomeModel())
    private val _selectedAccountNo = mutableStateOf("")
    val homeModel: State<HomeModel> = _homeModel
    val selectedAccountNo: State<String> = _selectedAccountNo
    private val TAG: String = "HomeViewModel"

    init {
        getMySavingAccountList(loginId)
    }

    fun onClickItem(accountNo: String) {
        _selectedAccountNo.value = accountNo
    }
    fun initSelectedAccountNo() {
        _selectedAccountNo.value = ""
    }

    fun logout(activity: MainActivity) {
        activity.logout()
    }

    // 나의 적금 계좌 목록 조회 =
    fun getMySavingAccountList(
        loginId: String,
    ) {
        viewModelScope.launch {
            // coroutine
            try {
                // todo
                val requestBody = MyAccountListRequestBody(loginId)
                val response = apiService.getMyAccountList(requestBody)
                Log.d(TAG, "response $response")
                Log.d(TAG, "requestBody $requestBody")
                MainActivity.preferences.getString("loginId", "")
                MainActivity.initUserUuidIfNull()
                if (response.success) {
                    _homeModel.value = HomeModel(
                        accountList = response.data,
                        hasError = false
                    )
                } else {
//                    _homeModel.value = HomeModel(
//                        accountList = listOf(
//                            MyAccount(
//                                "ESG 실천 챌린지형 적금",
//                                "2024.08.18",
//                                "2025.08.17",
//                                "1234-123-12345",
//                                "1회차",
//                                "2.6",
//                                "500000L"
//                            ) { onClickItem(accountNo = "1234-123-12345") },
//                            MyAccount(
//                                "웰니스 챌린지형 적금",
//                                "2024.08.18",
//                                "2025.08.17",
//                                "1234-123-12345",
//                                "1회차",
//                                "2.6",
//                                "1000000L"
//                            ) { onClickItem(accountNo = "1234-123-12345") },
//                        ),
//                        hasError = false
//                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "[/api/v1/save/create/savingaccount] API ERROR OCCURED message:", e)
                _homeModel.value = HomeModel(hasError = true)
            }
        }
    }
}

data class MyDepositAccountListModel(
    val depositAccountList: List<DepositAccount> = emptyList(),
    val hasError: Boolean = false
)

data class HomeModel(
    val accountList: List<MyAccount> = emptyList(),
    val hasError: Boolean = false
)
