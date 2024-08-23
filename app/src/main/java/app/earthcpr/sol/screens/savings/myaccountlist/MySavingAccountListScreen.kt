package app.earthcpr.sol.screens.savings.myaccountlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import app.earthcpr.sol.models.MyAccount
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun MySavingAccountListScreen(
    navigationToHomeScreen: () -> Unit,
    navigationToAccountDetailScreen: (String) -> Unit,
) {
    val context = LocalContext.current
    val mySavingAccountListViewModel: MySavingAccountListViewModel = viewModel()
    val myAccountListModel by mySavingAccountListViewModel.myAccountListModel
    val selectedAccountNo by mySavingAccountListViewModel.selectedAccountNo

    val scrollState = rememberScrollState()
    if (selectedAccountNo.isNotEmpty()) {
        navigationToAccountDetailScreen(selectedAccountNo)
        mySavingAccountListViewModel.initSelectedAccountNo()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // 고정된 상단 바
        TopBar(title = "적금 계좌 목록") {
            navigationToHomeScreen()
        }
        Spacer(modifier = Modifier.height(24.dp)) // 헤더와 리스트 사이의 공간

        // 스크롤 가능한 리스트
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            myAccountListModel.accountList.forEach { account ->
                MySavingAccount(account)
            }
        }
    }

}

@Composable
fun MySavingAccount(account: MyAccount) {
    Card(
        modifier = Modifier
            .height(210.dp)
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            .fillMaxWidth()
            .clickable {
                account.onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, color = colorResource(id = R.color.box_border_color)),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(start = 14.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = account.accountName,
                fontSize = 16.sp,
                color = Color(0xFF00201C),
                fontFamily = newFontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "가입 기간: ",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = account.accountCreateDate + " ~ " + account.accountExpiryDate,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "계좌번호: ",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = account.accountNo,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "납입 회차: ",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = account.interestNumber,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "적용 금리: ",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = account.interestRate.toString() + "%",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "누적 납입 금액",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.account_list_regular_text_color),
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.width(50.dp))
                // 원하는 로케일을 지정
                val locale = Locale("ko") // 또는 Locale("en", "US") 등
                val symbols = DecimalFormatSymbols(locale)
                val decimalFormat = DecimalFormat("#,###", symbols)

                Text(
                    text = decimalFormat.format(account.totalBalance) + "원",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}
