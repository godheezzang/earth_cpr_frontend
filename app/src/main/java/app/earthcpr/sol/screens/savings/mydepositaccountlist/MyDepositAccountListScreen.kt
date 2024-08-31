package app.earthcpr.sol.screens.savings.mydepositaccountlist

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import app.earthcpr.sol.R
import app.earthcpr.sol.models.DepositAccount
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun MyDepositAccountListScreen(
    navigationToHomeScreen: () -> Unit,
    navigationToSavingCreateScreen: (String, String) -> Unit,
    accountTypeUniqueNo: String
) {
    val context = LocalContext.current
    val myDepositAccountListViewModel: MyDepositAccountListViewModel = viewModel()
    val myAccountListModel by myDepositAccountListViewModel.myDepositAccountList
    var selectedAccountNo by remember { mutableStateOf("") }
    var enableNextButton by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    fun setSelectedDepositAccount(accountNo: String) {
        selectedAccountNo = accountNo
    }
    enableNextButton = selectedAccountNo.isNotEmpty()
    val nextButtonColor = if (!enableNextButton) Color(0xFF80A2FF) else Color(0xFF0046FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // 고정된 상단 바
        TopBar(title = "적금 가입") {
            navigationToHomeScreen()
        }
        Spacer(modifier = Modifier.height(108.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(16.dp)
        ) {
            Text(
                text = "어떤 계좌에서 출금할까요?",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = newFontFamily,
            )
            Spacer(modifier = Modifier.height(26.dp)) // 헤더와 리스트 사이의 공간

            Text(
                text = "출금계좌",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = newFontFamily,
            )

            Spacer(modifier = Modifier.height(13.dp)) // 헤더와 리스트 사이의 공간

            // 스크롤 가능한 리스트
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                myAccountListModel.depositAccountList.forEach { account ->
                    MyDepositAccount(
                        account,
                        { setSelectedDepositAccount(account.accountNo) },
                        isSelected = selectedAccountNo == account.accountNo
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
                            // todo
                            // savingMonthViewModel.createSavingAccount({navigationToSavingAccountSuccessScreen()}, depositBalance)
                            navigationToSavingCreateScreen(accountTypeUniqueNo, selectedAccountNo)
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                androidx.compose.material.Text(
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

@Composable
fun MyDepositAccount(param: DepositAccount, onClick: () -> Unit, isSelected: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(3.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            1.dp,
            color = if (isSelected) colorResource(id = R.color.box_border_selected_color) else colorResource(
                id = R.color.box_border_color
            )
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = param.accountNo,
                        fontFamily = newFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = if (isSelected) colorResource(id = R.color.box_border_selected_color) else colorResource(
                            id = R.color.box_border_color
                        )
                    )

                    Text(
                        text = param.accountName,
                        fontFamily = newFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = if (isSelected) colorResource(id = R.color.box_border_selected_color) else colorResource(
                            id = R.color.box_border_color
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    // 원하는 로케일을 지정
//                    val locale = Locale("ko") // 또는 Locale("en", "US") 등
//                    val symbols = DecimalFormatSymbols(locale)
//                    val decimalFormat = DecimalFormat("#,###", symbols)
//
//                    Text(
//                        text = "${decimalFormat.format(param.accountBalance)}원",
//                        fontFamily = newFontFamily,
//                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
//                        fontSize = 14.sp,
//                    )
                    val balance = param.getAccountBalanceAsLong()
                    val formattedBalance = DecimalFormat("#,###", DecimalFormatSymbols(Locale("ko"))).format(balance)

                    Text(
                        text = formattedBalance+"원",
                        fontFamily = newFontFamily,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                }

            }
        }


    }
}
