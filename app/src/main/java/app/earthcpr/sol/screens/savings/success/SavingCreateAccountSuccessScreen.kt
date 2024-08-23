package app.earthcpr.sol.screens.savings.success

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily

@Composable
fun SavingCreateAccountSuccessScreen(
    navigationToHomeScreen: () -> Unit,
    navigationToMyAccountListScreen: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(title = "적금 가입") {
            navigationToHomeScreen()
        }

        Spacer(modifier = Modifier.height(108.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(14.dp)
        ) {
            Text(
                text = "적금가입이 완료되었어요.",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = newFontFamily,
            )

            Spacer(modifier = Modifier.height(27.dp))

            Text(
                text = "상세 내역 확인은 적금 계좌 목록에서 확인할 수 있어요.",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = newFontFamily,
            )
        }

        Card(
            modifier = Modifier
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0046FF),
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navigationToMyAccountListScreen()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "완료",
                    fontFamily = newFontFamily,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(27.dp))

    }
}