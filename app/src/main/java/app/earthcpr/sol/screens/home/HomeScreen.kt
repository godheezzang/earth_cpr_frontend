package app.earthcpr.sol.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.earthcpr.sol.MainActivity
import app.earthcpr.sol.R
import app.earthcpr.sol.screens.savings.myaccountlist.MySavingAccount
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily

@Composable
fun HomeScreen(
    navigationToAccountDetailScreen: (String) -> Unit,
    navigationToProductListScreen: () -> Unit,
    navigationToChallengeHistoryScreen: () -> Unit,
    navigationToMiracleMorningVerificactionScreen: () -> Unit,
    navigationToTumblerVerificationScreen: () -> Unit,
    navigationToWorkOutVerificationScreen: () -> Unit

) {
    val context = LocalContext.current
    val activity = LocalContext.current as MainActivity
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
    ) {
        TopBar(title = "마이", showHomeButton = false)
        Spacer(modifier = Modifier.height(16.dp))

        val hasSavings = homeModel.accountList.isNotEmpty()
//        val hasSavings = false
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (hasSavings) {
                // 적금 목록
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = "챌린지",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )

                    Card(
                        modifier = Modifier
                            .height(210.dp)
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        border = BorderStroke(1.dp, color = colorResource(id = R.color.box_border_color)),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(16.dp)

                        ) {
                            Text(
                                text = "챌린지 바로가기",
                                fontFamily = newFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                            )
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // TODO 챌린지 검증 바로가기 로직 추가
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            navigationToMiracleMorningVerificactionScreen()
                                        },
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .width(24.dp)
                                            .height(24.dp),
                                        painter = painterResource(id = R.drawable.alarm),
                                        contentDescription = ""
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "미라클 모닝",
                                        fontFamily = newFontFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            navigationToTumblerVerificationScreen()
                                        },
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .width(24.dp)
                                            .height(24.dp),
                                        painter = painterResource(id = R.drawable.glass_cup),
                                        contentDescription = ""
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "텀블러 사용",
                                        fontFamily = newFontFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            navigationToWorkOutVerificationScreen()
                                        },
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .width(24.dp)
                                            .height(24.dp),
                                        painter = painterResource(id = R.drawable.fitness_center),
                                        contentDescription = ""
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "운동",
                                        fontFamily = newFontFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                    )

                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "챌린지 달성 내역 >",
                                fontFamily = newFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                modifier = Modifier.clickable {
                                    navigationToChallengeHistoryScreen()
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "적금",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )

                    homeModel.accountList.forEach { account ->
                        MySavingAccount(account)
                    }
                }
            } else {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SavingsEmptyLayout(
                        navigationToProductListScreen
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "로그아웃",
                fontSize = 16.sp,
                fontFamily = newFontFamily,
                color = Color.DarkGray,
                modifier = Modifier.clickable {
                    homeViewModel.logout(activity)
                }
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
            onClick = {
                navigationToProductListScreen()
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("적금 가입하기")
        }
    }
}