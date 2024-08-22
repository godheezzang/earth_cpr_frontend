package app.earthcpr.sol.services

import app.earthcpr.sol.models.ApiResponse
import app.earthcpr.sol.models.JoinRequestBody
import app.earthcpr.sol.models.LoginRequestBody
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
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
    .baseUrl("http://127.0.0.1:8080") // todo 서버 배포되면 개발서버 주소로 변경
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
    @POST("/login")
    suspend fun postLogin(@Body request: LoginRequestBody): ApiResponse<String>

    // [API] 회원가입
    @POST("/user")
    suspend fun postJoin(@Body request: JoinRequestBody): ApiResponse<String>
}