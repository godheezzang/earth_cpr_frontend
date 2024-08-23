package app.earthcpr.sol.screens.savings.month

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavingMonthSelectViewModel @Inject constructor() : ViewModel() {
    private val _isFailState = mutableStateOf(false)
    private val TAG: String = "SavingMonthSelectViewModel"

    // 적금 계좌 생성
    fun createSavingAccount(
        onSuccess: () -> Unit,
        depositBalance: String,

    ) {
        viewModelScope.launch {
            // coroutine
            try {
//                val requestBody = CreateAccountRequestBody(email)
//                val userUuid = apiService.postSavingAccount(requestBody).result
//                MainActivity.preferences.setString("userUuid", userUuid ?: "")
//                MainActivity.initUserUuidIfNull()
                onSuccess()
            } catch (e: Exception) {
                Log.e(TAG, "[/api/v1/save/create/savingaccount] API ERROR OCCURED")
            }

        }
    }
}