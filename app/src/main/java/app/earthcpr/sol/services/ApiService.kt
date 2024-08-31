package app.earthcpr.sol.services

import app.earthcpr.sol.models.api.request.CreateAccountRequestBody
import app.earthcpr.sol.models.api.response.ApiResponse
import app.earthcpr.sol.models.api.request.JoinRequestBody
import app.earthcpr.sol.models.api.request.LoginRequestBody
import app.earthcpr.sol.models.api.request.MyAccountListRequestBody
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val httpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addInterceptor { chain ->
        val request = chain.request()
        chain.proceed(request)
    }
    .build()

// dateTime을 parsing 하는 설정
val gson = GsonBuilder()
    .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
        LocalDateTime.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
    })
    .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ ->
        LocalDate.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    })
    .registerTypeAdapter(LocalTime::class.java, JsonDeserializer { json, _, _ ->
        LocalTime.parse(json.asString, DateTimeFormatter.ofPattern("HH:mm:ss"))
    })
    .registerTypeAdapter(Duration::class.java, JsonDeserializer { json, _, _ ->
        Duration.parse(json.asString)
    })
    .create()

// 엔드포인트를 준비시키고, json converter를 추가해주는 역할을 한다.
private val retrofit = Retrofit.Builder()
    .baseUrl("http://ec2-3-34-227-48.ap-northeast-2.compute.amazonaws.com:8080/api/v1") // todo 서버 배포되면 개발서버 주소로 변경
    .client(httpClient)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()


// 서비스 메소드에 대한 액세스 권한을 위임한다.
// recipeService 가 ApiService 의 getCategories를 호출할 수 있게 해준다.
// ApiService 내에 여러 fun 들을 추가하면 된다.
val apiService = retrofit.create(ApiService::class.java)
interface ApiService {

    // get, post 예시코드
//    @GET("/coin")
//    suspend fun getUserCoin(@Query("userUuid") userUuid: String): ApiResponse<UserCoinResponse>

//    @POST("/reward/daily")
//    suspend fun postDailyReward(@Body request: UserIdRequest): ApiResponse<String>

    // [API] 로그인
    @POST("/user/login")
    suspend fun postLogin(@Body request: LoginRequestBody): ApiResponse<String?>

    // [API] 회원가입
    @POST("/user/create")
    suspend fun postJoin(@Body request: JoinRequestBody): ApiResponse<String>

    // [API] 적금 계좌 등록
    @POST("/save/create/savingaccount")
    suspend fun postSavingAccount(@Body request: CreateAccountRequestBody): ApiResponse<String>

    // [API] 적금 계좌 목록 조회
    @GET("/save/get/savingaccount")
    suspend fun getMyAccountList(@Body request: MyAccountListRequestBody): ApiResponse<String>
}