package app.earthcpr.sol.screens.savings.accountdetail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.earthcpr.sol.models.AccountDetail
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavingAccountDetailViewModel @Inject constructor() : ViewModel() {
    private val _accountDetail = mutableStateOf(AccountDetail())
    val accountDetail: State<AccountDetail> = _accountDetail
    private val TAG: String = "SavingAccountDetailViewModel"

    // 적금 계좌 상세 조회 =
    fun getSavingAccountDetail(accountNo: String) {
        viewModelScope.launch {
            // coroutine
            try {
                // todo
//                val requestBody = AccountDetailRequestBody(accountNo)
//                val userUuid = apiService.postSavingAccount(requestBody).result
//                MainActivity.preferences.setString("userUuid", userUuid ?: "")
//                MainActivity.initUserUuidIfNull()
                _accountDetail.value = AccountDetail(
                    "ESG 챌린지형 적금",
                    "일상 속에서 ESG를 실천하고, 챌린지에 참가할수록 더 큰 보상을 받을 수 있는 ESG 저축 상품",
                    "1234-123-12345",
                    "신한은행",
                    "2024.08.18",
                    "2025.08.17",
                    "농협은행",
                    "1234-05-12345",
                    "1회차",
                    2.6,
                    1000000
                )
            } catch (e: Exception) {
                Log.e(TAG, "[/api/v1/save/create/savingaccount] API ERROR OCCURED")
            }

        }
    }
}