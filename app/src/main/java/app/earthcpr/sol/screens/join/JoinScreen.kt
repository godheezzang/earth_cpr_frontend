package app.earthcpr.sol.screens.join

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.earthcpr.sol.ui.theme.newFontFamily

@Composable
fun JoinScreen(
    navigationToLoginScreen: () -> Unit,
) {
    val joinViewModel: JoinViewModel = viewModel()
    val isEmailAlreadyJoined by joinViewModel.isEmailAlreadyJoined
    val isPasswordTooShort by joinViewModel.isPasswordTooShort
    val isUserNameEmpty by joinViewModel.isUserNameEmpty
    val joinButtonEnable by joinViewModel.joinButtonEnable
    Log.d("annmj", joinButtonEnable.toString())
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val joinButtonColor = if (!joinButtonEnable) Color(0xFF80A2FF) else Color(0xFF0046FF)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "회원가입",
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = newFontFamily,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(53.dp))

        // 사용자 이름 입력 필드
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "이메일",
                color = Color(0xFF6C6C6C),
                fontFamily = newFontFamily,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        TextField(
            value = email,
            onValueChange = {
                email = it
                joinViewModel.checkEmailLength(email)
            },
            placeholder = {
                Text(
                    text = "이메일을 입력해주세요.",
                    color = Color.Gray,
                    fontFamily = newFontFamily,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(56.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,  // 포커스 시 하단 선 색상
                unfocusedIndicatorColor = Color.Gray, // 비포커스 시 하단 선 색상
                textColor = Color.Black,
                cursorColor = Color.Black
            )
        )
        if (isEmailAlreadyJoined) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "*이미 가입된 이메일 주소입니다.",
                    color = Color(0xFFFF6161),
                    fontFamily = newFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "비밀번호",
                color = Color(0xFF6C6C6C),
                fontFamily = newFontFamily,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        TextField(
            value = password,
            onValueChange = {
                password = it
                joinViewModel.checkPasswordLength(password)
            },
            placeholder = {
                Text(
                    text = "비밀번호를 입력해주세요.",
                    color = Color.Gray,
                    fontFamily = newFontFamily,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(56.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,  // 포커스 시 하단 선 색상
                unfocusedIndicatorColor = Color.Gray, // 비포커스 시 하단 선 색상
                textColor = Color.Black,
                cursorColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        )
        if (isPasswordTooShort) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "*비밀번호는 8자 이상이어 합니다.",
                    color = Color(0xFFFF6161),
                    fontFamily = newFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "사용자 이름",
                color = Color(0xFF6C6C6C),
                fontFamily = newFontFamily,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        TextField(
            value = userName,
            onValueChange = {
                userName = it
                joinViewModel.checkUserNameLength(userName)
            },
            placeholder = {
                Text(
                    text = "이름을 입력해주세요.",
                    color = Color.Gray,
                    fontFamily = newFontFamily,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(56.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,  // 포커스 시 하단 선 색상
                unfocusedIndicatorColor = Color.Gray, // 비포커스 시 하단 선 색상
                textColor = Color.Black,
                cursorColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        )
        if (isUserNameEmpty) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "*사용자이름을 입력해주세요.",
                    color = Color(0xFFFF6161),
                    fontFamily = newFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 다음 버튼
        Card(
            modifier = Modifier
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = joinButtonColor,
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (joinButtonEnable) {
                            joinViewModel.createUser(
                                navigationToLoginScreen,
                                email,
                                password,
                                userName
                            )
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "회원가입",
                    fontFamily = newFontFamily,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))


    }
}

fun createAccount(email: String, password: String, navigate: () -> Unit) {

}

@Composable
fun Greeting() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(
                start = 3.dp,
                end = 3.dp,
                top = 21.dp,
                bottom = 11.dp
            )
    ) {
        Text(
            text = "계정을 생성하시게 되면",
            color = Color(0xFF9D9D9D),
        )

        Spacer(modifier = Modifier.height(1.dp))

        Row {
            Text(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height - 0.5.sp.toPx()
                        drawLine(
                            color = Color(0xFF9D9D9D),
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
                text = "이용 약관",
                color = Color(0xFF9D9D9D)
            )
            Text(
                text = " 및 ",
                color = Color(0xFF9D9D9D),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = "개인정보 보호 정책",
                color = Color(0xFF9D9D9D),
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height - 0.5.sp.toPx()
                        drawLine(
                            color = Color(0xFF9D9D9D),
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    }
            )
            Text(
                text = "에 동의하는 것입니다.",
                color = Color(0xFF9D9D9D),
                modifier = Modifier.padding(bottom = 12.dp)
            )

        }

    }
}

