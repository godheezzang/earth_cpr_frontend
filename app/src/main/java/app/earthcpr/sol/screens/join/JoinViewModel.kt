package app.earthcpr.sol.screens.join

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.earthcpr.sol.models.api.request.JoinRequestBody
import kotlinx.coroutines.launch
import javax.inject.Inject

class JoinViewModel @Inject constructor() : ViewModel() {
    private val TAG: String = "JoinViewModel"

    private val _isEmailAlreadyJoined = mutableStateOf(false)
    private val _isPasswordTooShort = mutableStateOf(true)
    private val _isUserNameEmpty = mutableStateOf(true)
    private val _isEmailEmpty = mutableStateOf(true)
    private val _joinButtonEnable = mutableStateOf(false)

    val isEmailAlreadyJoined: State<Boolean> = _isEmailAlreadyJoined
    val isEmailEmpty: State<Boolean> = _isEmailEmpty
    val isPasswordTooShort: State<Boolean> = _isPasswordTooShort
    val isUserNameEmpty: State<Boolean> = _isUserNameEmpty
    val joinButtonEnable: State<Boolean> = _joinButtonEnable

    fun checkPasswordLength(
        password: String
    ) {
        if (password.length < 8) {
            _isPasswordTooShort.value = true
        } else {
            _isPasswordTooShort.value = false
        }
        checkJoinButtonEnable()
    }

    fun checkUserNameLength(
        userName: String
    ) {
        _isUserNameEmpty.value = userName.isEmpty()
        checkJoinButtonEnable()
    }

    fun checkEmailLength(
        email: String
    ) {
        _isEmailEmpty.value = email.isEmpty()
        checkJoinButtonEnable()
    }

    private fun checkJoinButtonEnable(
    ) {
        _joinButtonEnable.value = !_isEmailEmpty.value &&
                !_isPasswordTooShort.value &&
                !_isUserNameEmpty.value
    }

    fun createUser(
        onSuccess: () -> Unit,
        userEmail: String,
        password: String,
        userName: String,
    ) {
        try {
            val requestBody = JoinRequestBody(userEmail, password, userName)
            viewModelScope.launch {
                // coroutine
                try {
                    // todo api 연동
//                    val response = apiService.postJoin(requestBody);
//                    if (response.result == "true") {
//                        // todo 성공시 onSuccess 호출
//                        // onSuccess()
//                    } else {
//                        // todo 제거
//                        MockService.doNothing()
//                        // todo
//                        // 이미 가입한 경우
//                        // _isEmailAlreadyJoined.value = true
//                    }
                    onSuccess()
                } catch (e: Exception) {
                    Log.e(TAG, "[ERROR] apiService.postJoin(requestBody) message: ", e)
                    // 회원가입 실패했습니다 처리
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "[ERROR] createUser message: ", e)
        }
    }
}