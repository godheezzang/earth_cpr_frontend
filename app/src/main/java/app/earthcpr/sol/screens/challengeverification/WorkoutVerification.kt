
package app.earthcpr.sol.screens.challengeverification

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.earthcpr.sol.R
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import java.io.File
import java.io.FileOutputStream


@Composable
fun WorkOutVerification(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var capturedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }

//    var navController = rememberNavController()

    val context = LocalContext.current

    var captureImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedImageBitmap = bitmap
        showDialog = false
    }

    var pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        showDialog = false
    }

//    Scaffold(
//        modifier = Modifier.background(color = Color.White),


//        topBar = {
//            TopBar(title = "영수증 검증")
//        },
//        content = { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
//                    .padding(paddingValues)
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // 촬영한 사진
        capturedImageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            ) // 갤러리 사진
        } ?: selectedImageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(context, uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            ) // 이미지 고르기 전
        } ?: Image(
            painter = painterResource(id = R.drawable.workout),  // 엥 베이스라인 .?
            contentDescription = "",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0044FF)
            ),
            modifier = Modifier
                .width(220.dp)
                .height(50.dp)
                .padding(bottom = 10.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("이미지 선택", color = Color.White, fontSize = 16.sp , fontFamily = newFontFamily,fontWeight = FontWeight.SemiBold,)
        }

        Button(
            onClick = {
                selectedImageUri?.let { //uri ->
//                    uploadImage(apiService, context, uri, "1", "1" ,  ){ success, error ->
//                        if (success) {
//                            errorMessage = "이미지 업로드 성공"
//                        } else {
//                            errorMessage = error
//                        }
//                    }
                    navController.navigate("WorkOutVerificationSuccessScreen")
                } ?: capturedImageBitmap?.let { //bitmap ->
//                    uploadImage(apiService, context, bitmap, "1", "1"){ success, error ->
//                        if (success) {
//                            errorMessage = "이미지 업로드 성공"
//                        } else {
//                            errorMessage = error
//                        }
//                    }
                    navController.navigate("WorkOutVerificationSuccessScreen")
                }
                navController.navigate("WorkOutVerificationSuccessScreen")

                Log.d("TAG", "이미지 전송 성공")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0044FF)),
            modifier = Modifier
                .width(220.dp)
                .height(50.dp)
                .padding(bottom = 10.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "이미지 전송", color = Color.White, fontSize = 16.sp , fontFamily = newFontFamily,fontWeight = FontWeight.SemiBold,)
        }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("이미지 업로드 방식 선택", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    captureImageLauncher.launch(null)
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.LightGray
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("사진 찍기", color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = {
                                    pickImageLauncher.launch("image/*")
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF0044FF)
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("사진 고르기")
                            }
                        }
                    }
                }
            }
        }
        errorMessage?.let { message ->
            if(message == "이미지 업로드 성공"){
                // errorMessage = null 처리 및 화면 전환
                errorMessage = null
                LaunchedEffect(Unit) {
//                            navController.navigate("ImageUploadSuccessScreen")
                    navController.navigate("ImageUploadSuccessScreen")
                }
            }else {
                AlertDialog(
                    onDismissRequest = { errorMessage = null },
                    title = { Text("오류 발생", color = Color.Red) },
                    text = { Text(message) },
                    confirmButton = {
                        Button(onClick = { errorMessage = null
//                            navController.navigate("ImageUploadSuccessScreen")
                        }) {
                            Text("확인")
                        }
                    }
                )
            }

        }  // errorMessage가 null일 떄 실행되게 하려고 했으나 상시 null일 때 떠버려서 실패
//                 ?: run{  AlertDialog(
//                onDismissRequest = { errorMessage = null },
//                title = { Text("업로드 성공", color = Color.Black) },
//                text = { Text("이미지 업로드 성공") },
//                confirmButton = {
//                    Button(onClick = { errorMessage = null }) {
//                        Text("확인")
//                    }
//                }
//                )
//                }
    }
//        }
//    )
}


// 제발 uri 업로드의 신이시여 오류나지 않게 도와주소서
private fun uploadImage(apiService: ApiService, context: android.content.Context, uri: Uri, userId: String, challengeId: String, onComplete: (Boolean, String?) -> Unit) {
    val userIdBody = RequestBody.create(MultipartBody.FORM, userId)
    val challengeIdBody = RequestBody.create(MultipartBody.FORM, challengeId)

    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "upload_image.jpg")
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    outputStream.close()

    val requestFile = RequestBody.create(MultipartBody.FORM, file)
    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
    val call = apiService.uploadReceipt(userIdBody, challengeIdBody, body)
    call.enqueue(object : retrofit2.Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
            if (response.isSuccessful) {
                onComplete(true, null)
                Log.d("TAG", "이미지 전송결과 성공 :  ${response.code()}  ${response.body()}")
            } else {
                onComplete(false, "이미지 전송이 실패하였습니다. 다시 시도해 주세요.")
                Log.d("TAG", "이미지 전송결과 실패 : ${response.code()}  ${response.body()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            onComplete(false, "이미지 전송이 실패하였습니다. 다시 시도해 주세요.")
            Log.d("TAG", "이미지 업로드 실패 onFailure : ${t.message}")

        }
    })
}
// 제발 bitmap 업로드의 신이시여 오류나지 않게 도와주소서
private fun uploadImage(apiService: ApiService, context: android.content.Context, bitmap: Bitmap, userId: String, challengeId: String, onComplete: (Boolean, String?) -> Unit ) {
    val userIdBody = RequestBody.create(MultipartBody.FORM, userId)
    val challengeIdBody = RequestBody.create(MultipartBody.FORM, challengeId)

    val file = File(context.cacheDir, "upload_image.jpg")
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.close()

    val requestFile = RequestBody.create(MultipartBody.FORM, file)
    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
    val call = apiService.uploadReceipt(userIdBody, challengeIdBody, body)
    call.enqueue(object : retrofit2.Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
            if (response.isSuccessful) {
                onComplete(true, null)
                Log.d("TAG", "이미지 전송 성공 : ${response.code()}  ${response.body()}")

            } else {
                onComplete(false, "이미지 전송이 실패하였습니다. 다시 시도해 주세요.")
                Log.d("TAG", "이미지 전송 시도 성공 , 통신 실패  , ${response.code()}  ${response.body()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            onComplete(false, "이미지 전송이 실패하였습니다. 다시 시도해 주세요.")
            Log.d("TAG", "이미지 업로드 실패 onFailure :   ${t.message}"  )
        }
    })
}

@Composable
fun WorkOutVerificationScreen(
    navController : NavController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(44.dp))

        TopBar(title = "운동 챌린지"  ) {
            navController.navigate("homeScreen")
        }

//        Spacer(modifier = Modifier.height(108.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(14.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // "탬플러를 사용하셨나요?" 텍스트
            Text(
                text = "운동을 하셨나요?",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontFamily = newFontFamily,
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // 설명 텍스트
            Text(
                text = "운동 이미지로 인증하고,\n챌린지를 달성하세요.",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    fontFamily = newFontFamily,
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 58.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {



                WorkOutVerification(navController = navController)



            }


        }

        Card(
            modifier = Modifier
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .fillMaxWidth()
                .clickable {
                    // 'homeScreen'으로 네비게이션
                    navController.navigate("challengeHistoryScreen")
                },
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0046FF),
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.navigate("homeScreen")
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "챌린지 목록",
                    fontFamily = newFontFamily,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(modifier = Modifier.height(27.dp))

    }
}




@Preview
@Composable
fun Previewhadadh(){
    WorkOutVerificationScreen(navController =  rememberNavController())
}
