package app.earthcpr.sol.screens.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import app.earthcpr.sol.R

@Composable
fun LineOf1Dp() {
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.top_bar_line_color))
    )
}
