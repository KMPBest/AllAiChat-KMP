package components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Wrapper(
  bgColor: Color = Color.Black,
  bottomSafeArea: Boolean = false,
  topSafeArea: Boolean = true,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  val systemBarPaddingValues = WindowInsets.systemBars.asPaddingValues()
  val paddingB = if (bottomSafeArea) systemBarPaddingValues.calculateBottomPadding() else 0.dp
  val paddingT = if (topSafeArea) systemBarPaddingValues.calculateTopPadding() else 0.dp

  Column(
    modifier =
      modifier.padding(
        bottom = paddingB,
        top = paddingT,
      ).background(bgColor).fillMaxSize(),
  ) {
    content()
  }
}
