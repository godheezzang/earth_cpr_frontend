package app.earthcpr.sol.screens.savings.money

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.earthcpr.sol.R
import app.earthcpr.sol.models.SavingMoney
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily

@Composable
fun SavingCreateScreen(
    navigationToHomeScreen: () -> Unit,
    navigationToMonthSelectScreen: (String) -> Unit,
    accountTypeUniqueNo: String
) {
    val context = LocalContext.current

    var enableTextArea by remember { mutableStateOf(false) }
    var enableCustomTextArea by remember { mutableStateOf(false) }
    var enableNextButton by remember { mutableStateOf(false) }
    var savingMoneyText by remember { mutableStateOf("") }

    fun setTextAreaIfExist(money: String) {
        enableTextArea = true
        savingMoneyText = money
    }

    fun enableTextArea(money: String) {
        enableTextArea = true
        enableCustomTextArea = true
        savingMoneyText = money
    }

    enableNextButton = savingMoneyText.isNotEmpty() && savingMoneyText.matches(Regex("\\d{1,3}(,\\d{3})*"))
    val nextButtonColor = if (!enableNextButton) Color(0xFF80A2FF) else Color(0xFF0046FF)

    val savingStartMoneyList = listOf(
        SavingMoney("1,000원", onClick = {setTextAreaIfExist("1,000")}, isSelected = savingMoneyText == "1,000"),
        SavingMoney("10,000원", onClick = {setTextAreaIfExist("10,000")}, isSelected = savingMoneyText == "10,000"),
        SavingMoney("50,000원", onClick = {setTextAreaIfExist("50,000")}, isSelected = savingMoneyText == "50,000"),
        SavingMoney("100,000원", onClick = {setTextAreaIfExist("100,000")}, isSelected = savingMoneyText == "100,000"),
        SavingMoney("500,000원", onClick = {setTextAreaIfExist("500,000")}, isSelected = savingMoneyText == "500,000"),
        SavingMoney("직접입력", onClick = {enableTextArea("금액을 입력해 주세요.")}, isSelected = enableCustomTextArea),
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
                text = "얼마로 시작할까요?",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = newFontFamily,
            )

            Spacer(modifier = Modifier.height(27.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SavingStartMoneyGrid(
                    savingStartMoneyList = savingStartMoneyList,
                    columns = 3,
                    modifier = Modifier.padding(2.dp)
                )
            }

            Spacer(modifier = Modifier.height(27.dp))

            if (enableTextArea) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(47.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.box_border_color),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        value = savingMoneyText,
                        onValueChange = { newText -> savingMoneyText = newText },
                        singleLine = true,
                    )
                }
            }
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
                            navigationToMonthSelectScreen(savingMoneyText)
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