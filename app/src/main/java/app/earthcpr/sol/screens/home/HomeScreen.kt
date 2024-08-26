package app.earthcpr.sol.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.earthcpr.sol.screens.topbar.TopBar

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF0F8))
            .padding(16.dp)
    ) {
        TopBar(title = "메인", showHomeButton = false)
        Spacer(modifier = Modifier.height(108.dp))

        val hasSavings = false
        if (hasSavings) {
            SavingsExistLayout()
        } else {
            SavingsEmptyLayout()
        }
    }
}

@Composable
fun SavingsExistLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        SavingsItem("ESG", "ESG 챌린지 적금", "500,000원")
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        SavingsItem("WELL", "웰니스 챌린지 적금", "500,000원")
    }
}

@Composable
fun SavingsItem(tag: String, title: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = tag,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = title,
                fontSize = 16.sp
            )
        }
        Text(
            text = amount,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SavingsEmptyLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "가입된 적금이 없습니다.",
            fontSize = 16.sp
        )
        Text(
            text = "적금을 가입하고 다양한 챌린지도 참여해보세요",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
        Button(
            onClick = { /* 적금 가입 페이지로 이동하는 로직 */ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("적금 가입하기")
        }
    }
}