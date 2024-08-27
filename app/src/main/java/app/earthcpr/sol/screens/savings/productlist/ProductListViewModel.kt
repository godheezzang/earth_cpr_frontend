package app.earthcpr.sol.screens.savings.productlist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.earthcpr.sol.models.ProductAccount
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor() : ViewModel() {
    // ViewModel 에서만 사용하는 변수
    // 상품 리스트
    private val _productListModel = mutableStateOf(ProductListModel())
    // 선택된 상품
    private val _selectedProductNo = mutableStateOf("")

    // 외부에 노출할 변수
    val productListModel: State<ProductListModel> = _productListModel
    val selectedProductNo: State<String> = _selectedProductNo

    private val TAG: String = "ProductListViewModel"

    // ViewModel 생성 시점에, 호출되는 메서드
    init {
        // 상품목록조회
        initProductList()
    }

    // api 를 불러와서 데이터를 초기화 시킨다.
    private fun initProductList() {
        // 어떤 api 불러야하지?
        viewModelScope.launch {
            // coroutine
            try {
                // todo
//                val requestBody = CreateAccountRequestBody(email)
//                val userUuid = apiService.postSavingAccount(requestBody).result
//                MainActivity.preferences.setString("userUuid", userUuid ?: "")
//                MainActivity.initUserUuidIfNull()
                _productListModel.value = ProductListModel(
                    productList = MockProductListApiData.productAccounts,
                    hasError = false
                )

            } catch (e: Exception) {
                Log.e(TAG, "[/api/v1/save/get/savingproducts] API ERROR OCCURED")
            }

        }
    }

    fun onClickItem(accountNo: String) {
        _selectedProductNo.value = accountNo
    }
    fun initSelectedAccountNo() {
        _selectedProductNo.value = ""
    }

}


data class ProductListModel(
    // 상품 리스트
    val productList: List<ProductAccount> = emptyList(),

    // productList 초기화에 실패할 경우 true, 성공 시 false
    val hasError: Boolean = false
)
