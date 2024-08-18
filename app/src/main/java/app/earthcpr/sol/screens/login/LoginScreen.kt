package app.earthcpr.sol.screens.login

import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.earthcpr.sol.R
import app.earthcpr.sol.ui.theme.newFontFamily

@Composable
fun LoginScreen(
    navigationToHomeScreen: () -> Unit,
) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel()
    val isFailState by loginViewModel.isFailState

    // 이미 로그인 했다면
    if (loginViewModel.checkAlreadyLogin()) {
        navigationToHomeScreen()
    }

    fun postLogin(): Unit {
        loginViewModel.login {
            // 홈 화면으로 이동
            navigationToHomeScreen()
        }
    }

    when (isFailState) {
        false -> {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var passwordVisibility by remember { mutableStateOf(false) }

            var loginButtonColor =
                if (password.isNotEmpty() && email.isNotEmpty()) Color(0xFF0046FF) else Color(0xFF80A2FF)

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
                        text = stringResource(id = R.string.app_korea_name),
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
                    onValueChange = { email = it },
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
                    onValueChange = { password = it },
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
                Spacer(modifier = Modifier.height(32.dp))

                // 다음 버튼
                Card(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = loginButtonColor,
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                // todo
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "로그인",
                            fontFamily = newFontFamily,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .clickable {
                            // todo
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "회원가입",
                        fontFamily = newFontFamily,
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light
                    )
                }

            }
        }

        true -> {
            Toast.makeText(
                context,
                "로그인에 실패하였습니다. 다시 시도해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            loginViewModel.failStateInit()
        }
    }
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

