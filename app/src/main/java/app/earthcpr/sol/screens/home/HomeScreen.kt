package app.earthcpr.sol.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.earthcpr.sol.screens.savings.myaccountlist.MySavingAccount
import app.earthcpr.sol.screens.topbar.TopBar

@Composable
fun HomeScreen(
    navigationToAccountDetailScreen: (String) -> Unit,
    navigationToProductListScreen: () -> Unit
) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel()
    val homeModel by homeViewModel.homeModel
    val selectedAccountNo by homeViewModel.selectedAccountNo

    val scrollState = rememberScrollState()
    if (selectedAccountNo.isNotEmpty()) {
        navigationToAccountDetailScreen(selectedAccountNo)
        homeViewModel.initSelectedAccountNo()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDF0F8))
            .padding(16.dp)
    ) {
        TopBar(title = "마이", showHomeButton = false)
        Spacer(modifier = Modifier.height(16.dp))

        val hasSavings = false
        if (hasSavings) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                homeModel.accountList.forEach { account ->
                    MySavingAccount(account)
                }
            }
        } else {
            SavingsEmptyLayout(
                navigationToProductListScreen()
            )
        }
    }
}


@Composable
fun SavingsEmptyLayout(
    navigationToProductListScreen: () -> Unit
) {
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