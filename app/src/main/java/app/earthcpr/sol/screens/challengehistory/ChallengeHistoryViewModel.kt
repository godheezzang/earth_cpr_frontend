package app.earthcpr.sol.screens.challengehistory

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.earthcpr.sol.models.ChallengeHistory
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChallengeHistoryViewModel @Inject constructor() : ViewModel() {
    private val _challengeHistoryModel = mutableStateOf(ChallengeHistoryModel())
    val challengeHistoryModel: State<ChallengeHistoryModel> = _challengeHistoryModel

    init {
        initChallengeHistoryList()

    }

    private fun initChallengeHistoryList() {
        viewModelScope.launch {
            try {
                _challengeHistoryModel.value = ChallengeHistoryModel(
                    challengeHistory = MockChallengeHistoryApiData.challengeHistoryList1,
                    hasError = false
                )
            } catch (e: Exception) {
                Log.e(TAG, "[/api/v1/save/get/challengehistory] API ERROR OCCURED")
            }
        }
    }
}

data class ChallengeHistoryModel(
    val challengeHistory: List<ChallengeHistory> = emptyList(),
    val hasError: Boolean = false
)

