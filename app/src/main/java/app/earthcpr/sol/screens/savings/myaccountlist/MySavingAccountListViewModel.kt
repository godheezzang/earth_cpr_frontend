package app.earthcpr.sol.screens.savings.myaccountlist

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
import app.earthcpr.sol.screens.savings.mydepositaccountlist.MyDepositAccountListModel
import app.earthcpr.sol.services.apiService
import kotlinx.coroutines.launch
import javax.inject.Inject

class MySavingAccountListViewModel @Inject constructor() : ViewModel() {
    private val _myAccountListModel = mutableStateOf(MyAccountListModel())
    private val _selectedAccountNo = mutableStateOf("")
    val myAccountListModel: State<MyAccountListModel> = _myAccountListModel
    val selectedAccountNo: State<String> = _selectedAccountNo
    private val TAG: String = "SavingAccountListViewModel"

    init {
        getMySavingAccountList(loginId)
    }

    fun onClickItem(accountNo: String) {
        _selectedAccountNo.value = accountNo
    }
    fun initSelectedAccountNo() {
        _selectedAccountNo.value = ""
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
                MainActivity.preferences.getString("loginId", "")
                MainActivity.initUserUuidIfNull()
                if (response.success) {
                    _myAccountListModel.value = MyAccountListModel(
                        accountList = response.data,
                        hasError = false
                    )
                } else {
                    _myAccountListModel.value = MyAccountListModel(hasError = true)
                }

            } catch (e: Exception) {
                Log.e(TAG, "[/api/v1/save/create/savingaccount] API ERROR OCCURED message:", e)
            }

        }
    }
}


data class MyAccountListModel(
    val accountList: List<MyAccount> = emptyList(),
    val hasError: Boolean = false
)

data class AccountListResponse(
    val success: Boolean,
    val data: List<MyAccount>
)