package app.earthcpr.sol.screens.savings.accountlist

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
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
import app.earthcpr.sol.models.ProductAccount
import app.earthcpr.sol.screens.savings.productlist.ProductListViewModel
import app.earthcpr.sol.screens.topbar.LineOf1Dp
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun ProductListScreen(
    navigationToHomeScreen: () -> Unit,
    navigationToMyDepositAccountListScreen: (String) -> Unit
) {
    val context = LocalContext.current
    val viewModel: ProductListViewModel = viewModel()
    // ViewModel에서 데이터를 가져옵니다.
    val productListModel = viewModel.productListModel.value

    val expandedCardStates = remember { mutableStateMapOf<Int, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // 고정된 상단 바
        TopBar(title = "적금 상품 조회") {
            navigationToHomeScreen()
        }
        Spacer(modifier = Modifier.height(24.dp)) // 헤더와 리스트 사이의 공간

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White)
        ) {
            productListModel.productList.forEachIndexed { index, cardInfo ->
                val isExpanded = expandedCardStates[index] ?: false

                SavingsCardWithToggle(
                    cardInfo = cardInfo,
                    isExpanded = isExpanded,
                    onToggle = {
                        expandedCardStates[index] = !isExpanded
                    },
                    navigationToMyDepositAccountListScreen
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SavingsCardWithToggle(
    cardInfo: ProductAccount,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    navigationToMyDepositAccountListScreen: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, color = colorResource(id = R.color.box_border_color)),
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cardInfo.accountName,
                    fontSize = 16.sp,
                    fontFamily = newFontFamily,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "(12개월 기준) 연 ${cardInfo.interestRate}% ~ ",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontFamily = newFontFamily,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "${cardInfo.interestRate.toDouble() + cardInfo.increaseInterestRate.toDouble()}%",
                        color = Color(0xFFD62020),
                        fontWeight = FontWeight.Normal,
                        fontFamily = newFontFamily,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cardInfo.accountDescription,
                fontFamily = newFontFamily,
                color = colorResource(id = R.color.account_list_regular_text_color),
                fontSize = 14.sp
            )
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                // 원하는 로케일을 지정
                val locale = Locale("ko") // 또는 Locale("en", "US") 등
                val symbols = DecimalFormatSymbols(locale)
                val decimalFormat = DecimalFormat("#,###", symbols)

                Text(
                    text = "저축 한도: 계좌별 월 ${decimalFormat.format(cardInfo.maxSubscriptionBalance)}만원",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = newFontFamily,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "기본 이자율 ",
                        fontFamily = newFontFamily,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "연 ${cardInfo.interestRate}%",
                        fontFamily = newFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.box_border_selected_color),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "최고 이자율 ",
                        fontFamily = newFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "연 ${cardInfo.interestRate.toDouble() + cardInfo.increaseInterestRate.toDouble()}%",
                        fontFamily = newFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.box_border_selected_color),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "챌린지와 함께 \n적금 완주에 도전해보세요",
                    fontFamily = newFontFamily,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "챌린지 인증 완료시 각 인증당 이자율이 높아져요.",
                    fontFamily = newFontFamily,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                cardInfo.challengeList?.forEach {
                    Text(
                        text = "• ${it.name}",
                        fontFamily = newFontFamily,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFBCE1EA),
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                Log.d("godhezzang", cardInfo.accountTypeUniqueNo)
                                navigationToMyDepositAccountListScreen(cardInfo.accountTypeUniqueNo)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "가입하기",
                            fontFamily = newFontFamily,
                            color = Color(0xFF006A85),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() }, // Handle card expansion/collapse on click
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LineOf1Dp()
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (isExpanded) "접기 ↑" else "펼쳐보기 ↓",
                    fontFamily = newFontFamily,
                    fontSize = 13.sp,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}