//package app.earthcpr.sol.screens.challengeverification.challengeCommonFunction
//
//import android.graphics.Bitmap
//import android.net.Uri
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import app.earthcpr.sol.screens.challengeverification.ReceiptVerification
//import app.earthcpr.sol.screens.topbar.TopBar
//import app.earthcpr.sol.services.ApiService
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import okhttp3.ResponseBody
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.Multipart
//import retrofit2.http.POST
//import retrofit2.http.Part
//import java.io.File
//import java.io.FileOutputStream
//import java.io.InputStream
//
//@Composable
//fun rememberImagePainter(context: android.content.Context, uri: Uri): androidx.compose.ui.graphics.painter.Painter {
//    return androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_report_image).let { defaultPainter ->
//        try {
//            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
//            val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
//            bitmap?.asImageBitmap()?.let { bitmapPainter ->
//                androidx.compose.ui.graphics.painter.BitmapPainter(bitmapPainter)
//            } ?: defaultPainter
//        } catch (e: Exception) {
//            defaultPainter
//        }
//    }
//}
//
//@Composable
//fun ImageUploadScreen(
//    navController : NavController
//) {
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.White)
//    ) {
//        Spacer(modifier = Modifier.height(44.dp))
//
//        TopBar(title = "텀블러 챌린지"  ) {
//
//        }
//
////        Spacer(modifier = Modifier.height(108.dp))
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .weight(1f)
//                .padding(14.dp)
//        ) {
//            Spacer(modifier = Modifier.height(40.dp))
//
//            // "탬플러를 사용하셨나요?" 텍스트
//            Text(
//                text = "텀블러를 사용하셨나요?",
//                style = TextStyle(
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                ),
//                textAlign = TextAlign.Left,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            // 설명 텍스트
//            Text(
//                text = "탬플러 사용 내역을 영수증으로 인증하고,\n챌린지를 달성하세요.",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Gray
//                ),
//                textAlign = TextAlign.Left,
//                modifier = Modifier.padding(bottom = 32.dp)
//            )
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 16.dp)
//                    .padding(vertical = 58.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//
//
//                ReceiptVerification(navController = navController)
//            }
//        }
//
//        Card(
//            modifier = Modifier
//                .height(50.dp)
//                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
//                .fillMaxWidth(),
//            colors = CardDefaults.cardColors(
//                containerColor = Color(0xFF0046FF),
//            )
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clickable {
//                    },
//                contentAlignment = Alignment.Center,
//            ) {
//                Text(
//                    text = "챌린지 목록",
//                    fontFamily = null,
//                    color = Color.White,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(27.dp))
//    }
//}
//
//// 제발 uri 업로드의 신이시여 오류나지 않게 도와주소서
//fun uploadImage(apiService: ApiService, context: android.content.Context, uri: Uri, userId: String, challengeId: String, onComplete: (Boolean, String?) -> Unit) {
//    val userIdBody = RequestBody.create(MultipartBody.FORM, userId)
//    val challengeIdBody = RequestBody.create(MultipartBody.FORM, challengeId)
//
//    val inputStream = context.contentResolver.openInputStream(uri)
//    val file = File(context.cacheDir, "upload_image.jpg")
//    val outputStream = FileOutputStream(file)
//    inputStream?.copyTo(outputStream)
//    outputStream.close()
//
//    val requestFile = RequestBody.create(MultipartBody.FORM, file)
//    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
//    val call = apiService.uploadReceipt(userIdBody, challengeIdBody, body)
//    call.enqueue(object : retrofit2.Callback<ResponseBody> {
//        override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
//            if (response.isSuccessful) {
//                onComplete(true, null)
//                Log.d("TAG", "이미지 전송결과 성공 :  ${response.code()}  ${response.body()}")
//            } else {
//                onComplete(false, "이미지 전송이 실패하였습니다. 다시 시도해 주세요.")
//                Log.d("TAG", "이미지 전송결과 실패 : ${response.code()}  ${response.body()}")
//            }
//        }
//
//        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//            onComplete(false, "이미지 전송이 실패하였습니다. 다시 시도해 주세요.")
//            Log.d("TAG", "이미지 업로드 실패 onFailure : ${t.message}")
//
//        }
//    })
//}
//// 제발 bitmap 업로드의 신이시여 오류나지 않게 도와주소서
//private fun uploadImage(apiService: ApiService, context: android.content.Context, bitmap: Bitmap, userId: String, challengeId: String, onComplete: (Boolean, String?) -> Unit ) {
//    val userIdBody = RequestBody.create(MultipartBody.FORM, userId)
//    val challengeIdBody = RequestBody.create(MultipartBody.FORM, challengeId)
//
//    val file = File(context.cacheDir, "upload_image.jpg")
//    val outputStream = FileOutputStream(file)
//    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//    outputStream.close()
//
//    val requestFile = RequestBody.create(MultipartBody.FORM, file)
//    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
//    val call = apiService.uploadReceipt(userIdBody, challengeIdBody, body)
//    call.enqueue(object : retrofit2.Callback<ResponseBody> {
//        override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
//            if (response.isSuccessful) {
//                onComplete(true, null)
//                Log.d("TAG", "이미지 전송 성공 : ${response.code()}  ${response.body()}")
//
//            } else {
//                onComplete(false, "이미지 전송이 실패하였습니다. 다시 시도해 주세요.")
//                Log.d("TAG", "이미지 전송 시도 성공 , 통신 실패  , ${response.code()}  ${response.body()}")
//            }
//        }
//
//        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//            onComplete(false, "이미지 전송이 실패하였습니다. 다시 시도해 주세요.")
//            Log.d("TAG", "이미지 업로드 실패 onFailure :   ${t.message}"  )
//        }
//    })
//}
//
//
//@Composable
//fun TumblerVerificationSuccessScreen(
//    navController : NavController, string1 : String, string2 : String
//) {
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.White)
//    ) {
//        Spacer(modifier = Modifier.height(44.dp))
//
//        TopBar(title = ""  ) {
//
//        }
//
////        Spacer(modifier = Modifier.height(108.dp))
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .weight(1f)
//                .padding(14.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 16.dp)
//                    .padding(vertical = 58.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                // "탬플러를 사용하셨나요?" 텍스트
//                Text(
//                    text = string1,
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black
//                    ),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                // 설명 텍스트
//                Text(
//                    text = "최고에요! 계속해서 챌린지에 도전하세요!.",
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal,
//                        color = Color.Gray
//                    ),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(bottom = 32.dp)
//                )
//
//                // 체크마크 아이콘
//                Icon(
//                    imageVector = Icons.Filled.CheckCircle,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .padding(bottom = 16.dp),
//                    tint = Color(0xFF007AFF)
//                )
//
//                // 인증 완료 메시지
//                Text(
//                    text = string2,
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black
//                    ),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(bottom = 58.dp)
//                )
//
//
//            }
//
//
//        }
//
//        Card(
//            modifier = Modifier
//                .height(50.dp)
//                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
//                .fillMaxWidth(),
//            colors = CardDefaults.cardColors(
//                containerColor = Color(0xFF0046FF),
//            )
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clickable {
//                    },
//                contentAlignment = Alignment.Center,
//            ) {
//                Text(
//                    text = "챌린지 목록",
//                    fontFamily = null,
//                    color = Color.White,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(27.dp))
//
//    }
//}
//
//
//interface ApiService {
//    @Multipart
//    @POST("/api/v1/challenge/verification")
//    fun uploadReceipt(
//        @Part("savingsAccountId")  savingsAccountId: RequestBody,
//        @Part("challengeId") challengeId: RequestBody,
//        @Part file: MultipartBody.Part
//    ): Call<ResponseBody>
//}
//
//val retrofit = Retrofit.Builder()
//    .baseUrl("http://ec2-52-78-171-40.ap-northeast-2.compute.amazonaws.com:8080/")
//    .addConverterFactory(GsonConverterFactory.create())
//    .build()
//
//val apiService = retrofit.create(ApiService::class.java)