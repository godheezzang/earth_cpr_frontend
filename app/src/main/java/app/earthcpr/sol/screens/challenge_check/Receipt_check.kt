package app.earthcpr.sol.screens.challenge_check
// 패키지명 변경해주기!!
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ht_practice.R
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

// API통신
interface ApiService {
    @Multipart
    @POST("upload_receipt")
    fun uploadReceipt(
        @Part("user_id") userId: RequestBody,
        @Part("challenge_id") challengeId: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>
}

// retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://your.api.url/") // 서버의 기본 URL
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

// 메인 함수
@Composable
fun ReceiptImageUploadScreen() {
    // 모달창 ON, OFF 상태관리
    var showDialog by remember { mutableStateOf(false) }
    // 갤러리,촬영 이미지 값 상태관리 ( uri, bitmap )
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var capturedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    // 촬영 기능
    val captureImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedImageBitmap = bitmap
        showDialog = false
    }

    // 갤러리에서 이미지 선택
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        showDialog = false
    }

    Scaffold(
        topBar = {
            //  CustomTopAppBarMutable() // 여기에 원하는 TopAppBar 컴포저블을 추가
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                // 촬영한 이미지를 보여주기
                capturedImageBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Captured Image",
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.White)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    ) // 촬영한 이미지가 null이면 갤러리 이미지 null유무 체크
                } ?: selectedImageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(context, uri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.White)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    ) // 둘 다 null이면 기본 이미지를 노출
                } ?: Image(painter =  painterResource(id = R.drawable.receipt_long ) , contentDescription = "" ,
                    modifier = Modifier
                        .size(200.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0044FF)),
                    modifier = Modifier
                        .width(220.dp)
                        .height(50.dp)
                        .padding(bottom = 16.dp),
                    shape = MaterialTheme.shapes.medium) {
                    Text("영수증 이미지 선택", color = Color.White, fontSize = 16.sp  )
                }
                // 이미지 전송 버튼
                Button(
                    onClick = {
                        selectedImageUri?.let { uri ->
                            uploadImage(apiService, context, uri, "1", "1")    // user_id , challenge_id 는 임시로 1로 고정
                        } ?: capturedImageBitmap?.let { bitmap ->                               // 추후에 user_id 는 preference에서 가져오고,
                            uploadImage(apiService, context, bitmap, "1", "1")    // challenge_id는 타 값으로 고정해줄 예정
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
                    modifier = Modifier
                        .width(220.dp)
                        .height(50.dp)
                        .padding(bottom = 16.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "이미지 전송", color = Color.White, fontSize = 16.sp)
                }


//                Button(
//                    onClick = { /* TODO: 업로드 기능 추가 */ },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
//                    modifier = Modifier
//                        .width(220.dp)
//                        .height(50.dp)
//                        .padding(bottom = 16.dp),
//                    shape = MaterialTheme.shapes.medium
//                ) {
//                    Text(text = "영수증 사진 업로드하기", color = Color.White, fontSize = 16.sp)
//                }
                // 모달창 보여줌
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("이미지 업로드 방식 선택") },
                        text = {
                            Column {
                                Button(onClick = {
                                    captureImageLauncher.launch(null)
                                },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF0044FF))) {
                                    Text("사진 촬영")
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = {
                                    pickImageLauncher.launch("image/*")
                                },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF0044FF))) {
                                    Text("갤러리")
                                }
                            }
                        }, // 모달창 닫기 버튼
                        confirmButton = {
                            Button(onClick = { showDialog = false }) {
                                Text("닫기")
                            }
                        }
                    )
                }
            }
        }
    )
}

// 기본 이미지 보여주는 함수?????/  일단.. keep
@Composable
fun rememberImagePainter(context: android.content.Context, uri: Uri): androidx.compose.ui.graphics.painter.Painter {
    return androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_report_image).let { defaultPainter ->
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
            bitmap?.asImageBitmap()?.let { bitmapPainter ->
                androidx.compose.ui.graphics.painter.BitmapPainter(bitmapPainter)
            } ?: defaultPainter
        } catch (e: Exception) {
            defaultPainter
        }
    }
}

// UploadImage   // 가져온 url를 이미지로 변환해주는 작업
private fun uploadImage(apiService: ApiService, context: android.content.Context, uri: Uri, userId: String, challengeId: String) {
    val userIdBody = RequestBody.create(MultipartBody.FORM, userId)
    val challengeIdBody = RequestBody.create(MultipartBody.FORM, challengeId) // MediaType.parse("multipart/form-data")

    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "upload_image.jpg")
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    outputStream.close()

    val requestFile = RequestBody.create(MultipartBody.FORM, file)
    val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

    val call = apiService.uploadReceipt(userIdBody, challengeIdBody, body)
    call.enqueue(object : retrofit2.Callback<ResponseBody> {
        override  fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
            if (response.isSuccessful) {
                // 성공적으로 업로드됨
                Log.d("TAG","이미지 업로드 성공")
            } else {
                // 서버에서 오류 발생
                Log.d("TAG","이미지 업로드 실패")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            // 업로드 실패
            Log.d("TAG","이미지 업로드 실패 onFailure")
            
        }
    })
}
                    // 가져온 bitmap을 이미지로 변환해주는 작업
private fun uploadImage(apiService: ApiService, context: android.content.Context, bitmap: Bitmap, userId: String, challengeId: String) {
    val userIdBody = RequestBody.create(MultipartBody.FORM, userId)
    val challengeIdBody = RequestBody.create(MultipartBody.FORM, challengeId)

    val file = File(context.cacheDir, "upload_image.jpg")
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.close()

    val requestFile = RequestBody.create(MultipartBody.FORM, file)
    val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

    val call = apiService.uploadReceipt(userIdBody, challengeIdBody, body)
    call.enqueue(object : retrofit2.Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
            if (response.isSuccessful) {
                // 성공적으로 업로드됨
                Log.d("TAG","이미지 업로드 성공")
            } else {
                // 서버에서 오류 발생
                Log.d("TAG","이미지 업로드 실패")
            }
        }
        // 통신 오류시... 
        override  fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            // 업로드 실패
            Log.d("TAG","이미지 업로드 실패 onFailure")
        }
    })
}


@Preview(showBackground = true)
@Composable
fun PreviewReceiptImageUploadScreen() {
    ReceiptImageUploadScreen()
}
