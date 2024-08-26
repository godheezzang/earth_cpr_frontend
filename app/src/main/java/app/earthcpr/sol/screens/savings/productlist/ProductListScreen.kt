package app.earthcpr.sol.screens.savings.accountlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun ProductListScreen() {
    val viewModel: ProductListViewModel = viewModel()
    val scope = rememberCoroutineScope()

    // ViewModel에서 데이터를 가져옵니다.
    val savingsProduct = viewModel.savingsProduct

    // Composable을 위한 데이터가 비어있으면 ViewModel에서 데이터를 가져옵니다.
    if (savingsProduct == null) {
        scope.launch {
            viewModel.fetchSavingsProduct()
        }
    }

    // UI 레이아웃
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF0F8))  // 배경색 설정
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (savingsProduct != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = savingsProduct.accountName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF171D1B)
                )
                Text(
                    text = savingsProduct.accountDescription,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "가입 기간: ${savingsProduct.subscriptionPeriod}개월",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = savingsProduct.rateDescription,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이율: ${savingsProduct.interestRate}% ~ ${savingsProduct.increaseInterestRate}%",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        } else {
            CircularProgressIndicator()  // 데이터를 가져오는 동안 로딩 표시
        }
    }
}
