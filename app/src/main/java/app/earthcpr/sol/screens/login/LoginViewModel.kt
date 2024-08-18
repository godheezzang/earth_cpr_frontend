package app.earthcpr.sol.screens.login

import android.text.TextUtils
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.earthcpr.sol.MainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {
    private val _isFailState = mutableStateOf(false)
    private val TAG: String = "LoginViewModel"

    val isFailState: State<Boolean> = _isFailState
    fun checkAlreadyLogin(): Boolean {
        val userUuid = MainActivity.preferences.getString("userUuid", "")
        return !TextUtils.isEmpty(userUuid)
    }

    fun failStateInit() {
        _isFailState.value = false
    }

    fun login(
        onSuccess: () -> Unit
    ) {
        try {
            fetchPostLogin(onSuccess)
        } catch (e: Exception) {

        }
    }

    private fun fetchPostLogin(
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            // coroutine
            try {
//                val requestBody = LoginRequestBody(email)
//                val userUuid = apiService.postLogin(requestBody).result
//                MainActivity.preferences.setString("userUuid", userUuid ?: "")
//                MainActivity.initUserUuidIfNull()
                onSuccess()
            } catch (e: Exception) {
                Log.e(TAG, "[/login] API ERROR OCCURED")
            }

        }
    }
}