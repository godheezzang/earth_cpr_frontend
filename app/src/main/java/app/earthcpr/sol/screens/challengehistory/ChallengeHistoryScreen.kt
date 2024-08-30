package app.earthcpr.sol.screens.challengehistory

import android.telephony.UiccCardInfo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.earthcpr.sol.R
import app.earthcpr.sol.models.ChallengeHistory
import app.earthcpr.sol.screens.topbar.LineOf1Dp
import app.earthcpr.sol.screens.topbar.TopBar
import app.earthcpr.sol.ui.theme.newFontFamily
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ChallengeHistoryScreen(
    navigationToHomeScreen: () -> Unit,
) {
    val viewModel: ChallengeHistoryViewModel = viewModel()
    val challengeHistoryModel = viewModel.challengeHistoryModel.value

    val expandedCardStates = remember { mutableStateMapOf<Int, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(title = "챌린지 달성 내역") {
            navigationToHomeScreen()
        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White)
        ) {
            Text(
                text = "챌린지로 지금까지",
                fontFamily = newFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "0.6%",
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF0046FF)
                )
                Text(
                    text = " 이자가 증가했어요!",
                    fontFamily = newFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color.Black
                )

            }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White)
        ) {
            challengeHistoryModel.challengeHistory.forEachIndexed { index, cardInfo ->
                val isExpanded = expandedCardStates[index] ?: false

                ChallengeHistoryCardWithToggle(
                    cardInfo = cardInfo,
                    isExpanded = isExpanded,
                    onToggle = {
                        expandedCardStates[index] = !isExpanded
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        }
    }
}

@Composable
fun ChallengeHistoryCardWithToggle(
    cardInfo: ChallengeHistory,
    isExpanded: Boolean,
    onToggle: () -> Unit
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
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    text = cardInfo.name,
                    fontSize = 16.sp,
                    fontFamily = newFontFamily,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                cardInfo.localDateTimeList?.forEach { localDateTime ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Text(
                            text = cardInfo.info,
                            fontSize = 14.sp,
                            fontFamily = newFontFamily,
                            color = Color.DarkGray,
                        )
                        Text(
                            text = localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                            fontSize = 14.sp,
                            fontFamily = newFontFamily,
                            color = Color.DarkGray,
                        )
                        Text(
                            text = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                            fontSize = 14.sp,
                            fontFamily = newFontFamily,
                            color = Color.DarkGray,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

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
                androidx.compose.material.Text(
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