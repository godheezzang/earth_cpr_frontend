package app.earthcpr.sol.screens.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.earthcpr.sol.R
import app.earthcpr.sol.ui.theme.newFontFamily

@Composable
fun TopBar(
    title: String,
    showHomeButton: Boolean = true,
    navigationToHomeScreen: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            if (showHomeButton) {
                HomeScreenButton(navigationToHomeScreen)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = newFontFamily,
                    color = Color(0xFF171D1B)
                )
            }

        }
    }
    LineOf1Dp()
}

@Composable
fun HomeScreenButton(navigationToHomeScreen: (() -> Unit)?) {
    Box(
        modifier = Modifier
            .padding(start = 17.dp)
            .clickable {
                navigationToHomeScreen?.invoke()
            },
    ) {
        Image(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            painter = painterResource(id = R.drawable.home),
            contentDescription = ""
        )
    }

}
