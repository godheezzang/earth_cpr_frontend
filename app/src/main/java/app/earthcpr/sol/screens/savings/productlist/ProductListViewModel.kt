package app.earthcpr.sol.screens.savings.productlist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// 데이터 모델
data class SavingsProduct(
    val accountName: String,
    val accountDescription: String,
    val subscriptionPeriod: String,
    val interestRate: String,
    val increaseInterestRate: String,
    val rateDescription: String
)

// API 인터페이스
interface SavingsApiService {
    @POST("/api/v1/save/get/savingproducts")
    suspend fun getSavingsProducts(@Body request: Any): SavingsProduct
}

// ViewModel
class AccountListViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080")  // API 서버 주소
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(SavingsApiService::class.java)

    var savingsProduct: SavingsProduct? = null
        private set

    fun fetchSavingsProduct() {
        viewModelScope.launch {
            val response = apiService.getSavingsProducts(Any())
            savingsProduct = response
        }
    }
}
