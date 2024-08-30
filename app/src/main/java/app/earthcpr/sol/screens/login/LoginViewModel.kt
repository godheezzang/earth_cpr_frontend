package app.earthcpr.sol.screens.login

import android.text.TextUtils
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.earthcpr.sol.MainActivity
import app.earthcpr.sol.models.api.request.LoginRequestBody
import app.earthcpr.sol.services.apiService
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {
    private val _isFailState = mutableStateOf(false)
    private val TAG: String = "LoginViewModel"

    val isFailState: State<Boolean> = _isFailState
    fun checkAlreadyLogin(): Boolean {
        val userUuid = MainActivity.preferences.getString("loginId", "")
        return !TextUtils.isEmpty(userUuid)
    }

    fun failStateInit() {
        _isFailState.value = false
    }

    fun login(
        onSuccess: () -> Unit,
        loginId: String,
        password: String
    ) {
        try {
            fetchPostLogin(onSuccess, loginId, password)
        } catch (e: Exception) {
            Log.e(TAG, "[/login] API ERROR OCCURED")
        }
    }

    private fun fetchPostLogin(
        onSuccess: () -> Unit,
        loginId: String,
        password: String
    ) {
        viewModelScope.launch {
            // coroutine
            try {
                val requestBody = LoginRequestBody(loginId, password)
                val response = apiService.postLogin(requestBody)
                Log.d(TAG, "response: $response")


                MainActivity.preferences.setString("loginId", loginId)
//                MainActivity.initUserUuidIfNull()
                onSuccess()
            } catch (e: Exception) {
                Log.e(TAG, "[/login] API ERROR OCCURED: ${e.message}")
            }

        }
    }
}