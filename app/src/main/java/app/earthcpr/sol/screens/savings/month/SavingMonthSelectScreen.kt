package app.earthcpr.sol.screens.savings.month

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
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.earthcpr.sol.models.SavingMonth
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily

@Composable
fun SavingMonthSelectScreen(
    navigationToHomeScreen: () -> Unit,
    navigationToSavingAccountSuccessScreen: () -> Unit,
    depositBalance: String
) {
    val context = LocalContext.current
    val savingMonthViewModel: SavingMonthSelectViewModel = viewModel()

    var enableNextButton by remember { mutableStateOf(false) }
    var savingMonthPeriod by remember { mutableStateOf("") }

    fun setMonth(month: String) {
        savingMonthPeriod = month
    }

    enableNextButton = savingMonthPeriod.isNotEmpty()
    val nextButtonColor = if (!enableNextButton) Color(0xFF80A2FF) else Color(0xFF0046FF)

    val savingStartMonthList = listOf(
        SavingMonth("6개월", onClick = {setMonth("6")}, isSelected = savingMonthPeriod == "6"),
        SavingMonth("12개월", onClick = {setMonth("12")}, isSelected = savingMonthPeriod == "12"),
    )

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
                text = "언제까지 모아볼까요?",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = newFontFamily,
            )

            Spacer(modifier = Modifier.height(27.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SavingStartMonthGrid(
                    savingStartMonthList = savingStartMonthList,
                    columns = 2,
                    modifier = Modifier.padding(2.dp)
                )
            }

            Spacer(modifier = Modifier.height(27.dp))
        }


        Card(
            modifier = Modifier
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = nextButtonColor,
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (enableNextButton) {
                            // todo
                             // savingMonthViewModel.createSavingAccount({navigationToSavingAccountSuccessScreen()}, depositBalance)
                            navigationToSavingAccountSuccessScreen()
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "다음",
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