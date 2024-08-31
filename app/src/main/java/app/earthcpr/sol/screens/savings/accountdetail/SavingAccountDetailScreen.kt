package app.earthcpr.sol.screens.savings.accountdetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import app.earthcpr.sol.screens.topbar.LineOf1Dp
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily

@Composable
fun SavingAccountDetailScreen(
    navigationToHomeScreen: () -> Unit,
    accountNo: String,
    navigationToChallengeHistoryScreen: () -> Unit,
) {
    val context = LocalContext.current
    val savingAccountDetailViewModel: SavingAccountDetailViewModel = viewModel()
    savingAccountDetailViewModel.getSavingAccountDetail(accountNo)
    val accountDetail by savingAccountDetailViewModel.accountDetail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // 고정된 상단 바
        TopBar(title = "적금 계좌 상세") {
            navigationToHomeScreen()
        }
        Spacer(modifier = Modifier.height(24.dp)) // 헤더와 리스트 사이의 공간

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            ) {

                Text(
                    text = accountDetail.accountName,
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = accountDetail.accountDescription
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "계좌번호 " + accountDetail.accountNo + " " + accountDetail.bankName,
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF0046FF)
                )
                Spacer(modifier = Modifier.height(6.dp))

                LineOf1Dp()

                Spacer(modifier = Modifier.height(6.dp))

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
                        text = accountDetail.accountCreateDate + " ~ " + accountDetail.accountExpiryDate,
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
                        text = "출금은행명: ",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.account_list_regular_text_color),
                        fontFamily = newFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = accountDetail.withdrawalBankName,
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
                        text = "출금 계좌번호: ",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.account_list_regular_text_color),
                        fontFamily = newFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = accountDetail.withdrawalAccountNo,
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
                        text = accountDetail.interestNumber,
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
                        text = accountDetail.interestRate.toString() + "%",
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
                        text = "계좌 개설일: ",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.account_list_regular_text_color),
                        fontFamily = newFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = accountDetail.accountCreateDate,
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
                        text = "계좌 만기일: ",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.account_list_regular_text_color),
                        fontFamily = newFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = accountDetail.accountExpiryDate,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.account_list_regular_text_color),
                        fontFamily = newFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "챌린지 달성율이 궁금하다면?",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                LineOf1Dp()

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0046FF),
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navigationToChallengeHistoryScreen()
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        androidx.compose.material.Text(
                            text = "확인하러 가기",
                            fontFamily = newFontFamily,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

